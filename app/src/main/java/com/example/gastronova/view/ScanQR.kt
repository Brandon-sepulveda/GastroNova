package com.example.gastronova.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gastronova.controller.FavoritosViewModel
import com.example.gastronova.controller.RutaViewModel
import com.example.gastronova.model.RutaDto
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanQr(navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val rutaVm: RutaViewModel = viewModel()
    val favoritosVm: FavoritosViewModel = viewModel()

    val rutasState by rutaVm.listState.collectAsState()
    val favListState by favoritosVm.listState.collectAsState()
    val favSaveState by favoritosVm.saveState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Permiso cámara
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (!granted) {
            scope.launch { snackbarHostState.showSnackbar("Se requiere permiso de cámara para escanear QR") }
        }
    }

    // Evita lecturas repetidas frame a frame
    var lastPayload by remember { mutableStateOf<String?>(null) }
    var scanningEnabled by remember { mutableStateOf(true) }

    // Cargar rutas + favoritos al entrar
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
        rutaVm.cargarRutas()
        favoritosVm.cargarFavoritos()
    }

    // Mensajes de guardado
    LaunchedEffect(favSaveState.success) {
        if (favSaveState.success == true) {
            snackbarHostState.showSnackbar("Ruta agregada a favoritos")
            favoritosVm.limpiarSaveState()
            favoritosVm.cargarFavoritos()
            scanningEnabled = true
        }
    }

    LaunchedEffect(favSaveState.error) {
        favSaveState.error?.let { err ->
            snackbarHostState.showSnackbar(err)
            favoritosVm.limpiarSaveState()
            scanningEnabled = true
        }
    }

    fun normalize(s: String): String {
        val noAccents = Normalizer.normalize(s, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
        return noAccents.trim().lowercase().replace("\\s+".toRegex(), " ")
    }

    fun mapIndiceAListaNombre(indice: Int): String? {
        return when (indice) {
            1 -> "Ruta del ramen"
            2 -> "Ruta del completo"
            3 -> "Ruta a la italiana"
            4 -> "Ruta del sushi"
            5 -> "La gran ruta peruana"
            6 -> "Ruta del chocolate"
            else -> null
        }
    }

    fun resolverRutaDesdePayload(payloadRaw: String): RutaDto? {
        val payload = payloadRaw.trim()

        // 0) si aún no cargan rutas, no busques
        if (rutasState.loading || rutasState.data.isEmpty()) return null

        // 1) soporta "GN_RUTA|..."
        val cleaned = if (payload.startsWith("GN_RUTA|", ignoreCase = true)) {
            payload.substringAfter("|").trim()
        } else payload

        // 2) si viene "1..6" (índice), mapear a nombre SI NO existe por id real
        val maybeInt = cleaned.toIntOrNull()
        if (maybeInt != null) {
            // intento A: interpretarlo como ID real de DB
            val porId = rutasState.data.firstOrNull { it.id == maybeInt }
            if (porId != null) return porId

            // intento B: interpretarlo como índice 1..6
            val nombre = mapIndiceAListaNombre(maybeInt) ?: return null
            return rutasState.data.firstOrNull { normalize(it.nombre) == normalize(nombre) }
        }

        // 3) nombre directo
        return rutasState.data.firstOrNull { normalize(it.nombre) == normalize(cleaned) }
    }

    fun onQrDecoded(payload: String) {
        if (!scanningEnabled) return

        val payloadTrim = payload.trim()
        if (payloadTrim.isBlank()) return
        if (payloadTrim == lastPayload) return

        lastPayload = payloadTrim

        // si aún no cargan rutas
        if (rutasState.loading || rutasState.data.isEmpty()) {
            scope.launch { snackbarHostState.showSnackbar("Cargando rutas... intenta nuevamente") }
            return
        }

        val ruta = resolverRutaDesdePayload(payloadTrim)
        if (ruta?.id == null) {
            scope.launch {
                snackbarHostState.showSnackbar("QR leído, pero no coincide con una ruta válida")
            }
            return
        }

        // evitar duplicado en app
        val yaGuardada = favListState.data.any { it.id == ruta.id }
        if (yaGuardada) {
            scope.launch { snackbarHostState.showSnackbar("Esta ruta ya está en tus favoritos") }
            return
        }

        scanningEnabled = false
        scope.launch { snackbarHostState.showSnackbar("QR detectado: ${ruta.nombre}. Guardando...") }

        favoritosVm.guardar(ruta.id!!)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Escanear QR",
                style = MaterialTheme.typography.titleLarge
            )

            if (!hasCameraPermission) {
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Dar permiso de cámara") }
            } else {
                CameraQrPreview(
                    lifecycleOwner = lifecycleOwner,
                    onQrDecoded = ::onQrDecoded,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Button(
                onClick = { navController.navigate("homeUser") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

@Composable
private fun CameraQrPreview(
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    onQrDecoded: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(options)

        val analysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        analysis.setAnalyzer(executor) { imageProxy: ImageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage == null) {
                imageProxy.close()
                return@setAnalyzer
            }

            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val first = barcodes.firstOrNull()
                    val raw = first?.rawValue
                    if (!raw.isNullOrBlank()) {
                        onQrDecoded(raw)
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            analysis
        )
    }
}

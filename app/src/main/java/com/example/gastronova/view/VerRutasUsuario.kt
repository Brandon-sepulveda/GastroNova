package com.example.gastronova.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gastronova.R
import com.example.gastronova.controller.FavoritosViewModel
import com.example.gastronova.controller.RutaViewModel
import com.example.gastronova.model.RestaurantDto
import com.example.gastronova.model.RutaDto
import com.example.gastronova.view.components.RestaurantItems
import com.example.gastronova.view.components.Selector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerRutasUsuario(navController: NavHostController) {

    var rutaSeleccionadaNombre by remember { mutableStateOf("") }

    val rutaVm: RutaViewModel = viewModel()
    val favoritosVm: FavoritosViewModel = viewModel()

    val rutasState by rutaVm.listState.collectAsState()
    val favSaveState by favoritosVm.saveState.collectAsState()
    val favListState by favoritosVm.listState.collectAsState()
    var selectorRutaKey by remember { mutableStateOf(0) }


    val snackbarHostState = remember { SnackbarHostState() }

    // Cargar rutas + favoritos al entrar
    LaunchedEffect(Unit) {
        rutaVm.cargarRutas()
        favoritosVm.cargarFavoritos()
    }


    // Ruta seleccionada
    val rutaSeleccionada: RutaDto? =
        rutasState.data.firstOrNull { it.nombre == rutaSeleccionadaNombre }

    // ids ya guardadas
    val favoritosIds: Set<Int> = favListState.data.mapNotNull { it.id }.toSet()

    // nombres deshabilitados en el selector
    val disabledNames: Set<String> =
        rutasState.data.filter { it.id != null && favoritosIds.contains(it.id!!) }
            .map { it.nombre }
            .toSet()

    val rutasDisponibles: List<String> = rutasState.data.map { it.nombre }

    val rutaYaGuardada = rutaSeleccionada?.id?.let { favoritosIds.contains(it) } == true

    LaunchedEffect(favSaveState.success) {
        if (favSaveState.success == true) {
            snackbarHostState.showSnackbar("Se guardó en favoritos")

            rutaSeleccionadaNombre = ""

            selectorRutaKey++

            favoritosVm.limpiarSaveState()
            favoritosVm.cargarFavoritos()
        }
    }

    LaunchedEffect(favSaveState.error) {
        if (favSaveState.success == false && favSaveState.error != null) {
            snackbarHostState.showSnackbar("No se pudo guardar: ${favSaveState.error}")
            favoritosVm.limpiarSaveState()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.gato_mapa),
                contentDescription = "GatoMapa",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 110.dp)
                    .size(125.dp),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 125.dp)
                .navigationBarsPadding()
                .padding(innerPadding)
        ) {

            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Buscar ruta gastronómica",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (rutasState.loading) Text("Cargando rutas...")

                    if (rutasState.error != null) {
                        Text(
                            text = "Error al cargar rutas: ${rutasState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Selector(
                        label = "Ruta",
                        value = rutaSeleccionadaNombre,
                        options = rutasDisponibles,
                        onSelect = { rutaSeleccionadaNombre = it },
                        disabledOptions = disabledNames,
                        resetKey = selectorRutaKey,
                        modifier = Modifier.fillMaxWidth()
                    )



                    if (rutaYaGuardada) {
                        Text(
                            text = "Esta ruta ya está guardada en favoritos.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    if (rutaSeleccionada != null) {

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            thickness = 2.dp
                        )

                        Text(
                            text = rutaSeleccionada.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = rutaSeleccionada.descripcion?.takeIf { it.isNotBlank() }
                                ?: "Esta ruta aún no tiene descripción registrada.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            thickness = 2.dp
                        )

                        val restaurantesDeRuta: List<RestaurantDto> = rutaSeleccionada.restaurantes

                        Text(
                            text = "Restaurantes en la ruta:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (restaurantesDeRuta.isEmpty()) {
                            Text("Esta ruta aún no tiene restaurantes asociados.")
                        } else {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                restaurantesDeRuta.forEach { restaurante ->
                                    RestaurantItems(
                                        nombre = restaurante.nombre,
                                        direccion = restaurante.direccionText
                                            ?: restaurante.ubicacion
                                            ?: "Dirección no especificada",
                                        descripcion = restaurante.descripcion ?: "Sin descripción"
                                    )
                                }
                            }
                        }
                    }

                    // Botón guardar favoritos
                    Button(
                        onClick = {
                            rutaSeleccionada?.id?.let { rutaId ->
                                favoritosVm.guardar(rutaId)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        enabled = (rutaSeleccionada != null) && !rutaYaGuardada && !favSaveState.loading
                    ) {
                        Text(if (favSaveState.loading) "Guardando..." else "Guardar ruta")
                    }

                    Button(
                        onClick = { navController.navigate("homeUser") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

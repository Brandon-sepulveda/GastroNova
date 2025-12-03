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
import com.example.gastronova.model.RutaDto
import com.example.gastronova.view.components.RestaurantItems
import com.example.gastronova.view.components.Selector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasGuardadas(navController: NavHostController) {

    val vm: FavoritosViewModel = viewModel()
    val listState by vm.listState.collectAsState()
    val deleteState by vm.deleteState.collectAsState()

    var rutaSeleccionadaNombre by remember { mutableStateOf("") }

    // Cargar favoritos al entrar
    LaunchedEffect(Unit) {
        vm.cargarFavoritos()
    }

    // Si borramos una favorita, recargamos lista y limpiamos selección
    LaunchedEffect(deleteState.success) {
        if (deleteState.success == true) {
            rutaSeleccionadaNombre = ""
            vm.cargarFavoritos()
        }
    }

    val opcionesRutasGuardadas = listState.data.map { it.nombre }

    val rutaSeleccionada: RutaDto? =
        listState.data.firstOrNull { it.nombre == rutaSeleccionadaNombre }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->

        // Imagen del gato arriba
        Box(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.gato_mapa),
                contentDescription = "GatoMapa",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 110.dp)
                    .fillMaxWidth(0.3f),
                contentScale = ContentScale.Fit
            )
        }

        // Tarjeta principal
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
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Text(
                        text = "Mis rutas guardadas",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (listState.loading) {
                        Text("Cargando rutas guardadas...")
                    }

                    if (listState.error != null) {
                        Text(
                            text = "Error: ${listState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    if (!listState.loading && listState.data.isEmpty()) {
                        Text("Aún no tienes rutas guardadas.")
                    }

                    // Selector SOLO con rutas guardadas
                    if (listState.data.isNotEmpty()) {
                        Selector(
                            label = "Selecciona una ruta guardada",
                            value = rutaSeleccionadaNombre,
                            options = opcionesRutasGuardadas,
                            onSelect = { rutaSeleccionadaNombre = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Mostrar la ruta seleccionada abajo
                    if (rutaSeleccionada != null) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 2.dp
                        )

                        Text(
                            text = rutaSeleccionada.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = rutaSeleccionada.descripcion
                                ?: "Esta ruta no tiene descripción registrada.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Restaurantes en la ruta:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (rutaSeleccionada.restaurantes.isEmpty()) {
                            Text("No hay restaurantes asociados a esta ruta.")
                        } else {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                rutaSeleccionada.restaurantes.forEach { r ->
                                    RestaurantItems(
                                        nombre = r.nombre,
                                        direccion = r.direccionText
                                            ?: r.ubicacion
                                            ?: "Dirección no especificada",
                                        descripcion = r.descripcion ?: "Sin descripción"
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                rutaSeleccionada.id?.let { vm.eliminar(it) }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Eliminar de guardadas")
                        }

                        if (deleteState.success == false && deleteState.error != null) {
                            Text(
                                text = "No se pudo eliminar: ${deleteState.error}",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
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

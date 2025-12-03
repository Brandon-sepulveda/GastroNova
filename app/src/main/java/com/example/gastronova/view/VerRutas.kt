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
import com.example.gastronova.model.RutaDto
import com.example.gastronova.model.RestaurantDto
import com.example.gastronova.view.components.Selector
import com.example.gastronova.view.components.RestaurantItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerRutas(navController: NavHostController) {


    var rutaSeleccionadaNombre by remember { mutableStateOf("") }

    // ViewModels
    val rutaVm: RutaViewModel = viewModel()
    val favoritosVm: FavoritosViewModel = viewModel()

    val listaState by rutaVm.listState.collectAsState()

    // Cargar rutas desde el backend al entrar
    LaunchedEffect(Unit) {
        rutaVm.cargarRutas()
    }

    // Nombres de rutas disponibles desde el backend
    val rutasDisponibles: List<String> = listaState.data.map { it.nombre }

    // Ruta seleccionada según el nombre escogido
    val rutaSeleccionada: RutaDto? =
        listaState.data.firstOrNull { it.nombre == rutaSeleccionadaNombre }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->

        // Imagen del gato con mapa
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
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

        // Contenido principal
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

                    // Estado de carga
                    if (listaState.loading) {
                        Text("Cargando rutas...")
                    }

                    // Error al cargar
                    if (listaState.error != null) {
                        Text(
                            text = "Error al cargar rutas: ${listaState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    // Selector de ruta con datos desde backend
                    Selector(
                        label = "Ruta",
                        value = rutaSeleccionadaNombre,
                        options = rutasDisponibles,
                        onSelect = { rutaSeleccionadaNombre = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Si hay ruta seleccionada
                    if (rutaSeleccionada != null) {

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            thickness = 2.dp
                        )

                        // Nombre y descripción
                        Text(
                            text = rutaSeleccionada.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        if (!rutaSeleccionada.descripcion.isNullOrBlank()) {
                            Text(
                                text = rutaSeleccionada.descripcion!!,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else {
                            Text(
                                text = "Esta ruta aún no tiene descripción registrada.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            thickness = 2.dp
                        )

                        // Restaurantes asociados a la ruta
                        val restaurantesDeRuta: List<RestaurantDto> =
                            rutaSeleccionada.restaurantes

                        Text(
                            text = "Restaurantes en la ruta:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (restaurantesDeRuta.isEmpty()) {
                            Text(
                                text = "Esta ruta aún no tiene restaurantes asociados.",
                                style = MaterialTheme.typography.bodyMedium
                            )
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
                                        descripcion = restaurante.descripcion
                                            ?: "Sin descripción"
                                    )
                                }
                            }
                        }
                    }

                    // Botón volver
                    Button(
                        onClick = { navController.navigate("homeAdmin") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Volver")
                    }
                }
            }
        }
    }
}

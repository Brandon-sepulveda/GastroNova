package com.example.gastronova.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gastronova.R
import com.example.gastronova.controller.RestaurantViewModel
import com.example.gastronova.controller.RutaViewModel
import com.example.gastronova.model.RestaurantDto

private const val MAX_RESTAURANTS = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarRuta(navController: NavHostController) {

    // ViewModels
    val rutaVm: RutaViewModel = viewModel()
    val restaurantVm: RestaurantViewModel = viewModel()

    // Estados de los ViewModel
    val registerState by rutaVm.registerState.collectAsState()
    val restaurantListState by restaurantVm.listState.collectAsState()

    // Estados locales del formulario
    var nombreRuta by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // IDs de restaurantes seleccionados (como Long)
    var selectedRestaurantIds by remember { mutableStateOf(setOf<Long>()) }

    // Mensaje de validación (seleccionar exactamente 5)
    var selectionMsg by remember { mutableStateOf<String?>(null) }

    // Cargar restaurantes al entrar a la pantalla
    LaunchedEffect(Unit) {
        restaurantVm.cargarRestaurants()
    }

    // Si se registró bien, volvemos al homeAdmin
    LaunchedEffect(registerState.success) {
        if (registerState.success == true) {
            navController.navigate("homeAdmin") {
                popUpTo("RegistrarRuta") { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.gato_mapa),
                    contentDescription = "Gato con mapa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Registrar ruta gastronómica",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // NOMBRE DE LA RUTA
                OutlinedTextField(
                    value = nombreRuta,
                    onValueChange = { nombreRuta = it },
                    label = { Text("Nombre de la ruta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                // DESCRIPCIÓN
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                HorizontalDivider()

                Text(
                    text = "Selecciona los restaurantes de esta ruta:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Seleccionados: ${selectedRestaurantIds.size}/$MAX_RESTAURANTS",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedRestaurantIds.size == MAX_RESTAURANTS)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )

                if (selectedRestaurantIds.size != MAX_RESTAURANTS) {
                    Text(
                        text = "Debes seleccionar exactamente $MAX_RESTAURANTS restaurantes para poder guardar.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Estado restaurantes
                when {
                    restaurantListState.loading -> {
                        Text("Cargando restaurantes...")
                    }

                    restaurantListState.error != null -> {
                        Text(
                            text = "Error al cargar restaurantes: ${restaurantListState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    restaurantListState.data.isEmpty() -> {
                        Text("No hay restaurantes registrados aún.")
                    }

                    else -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            restaurantListState.data.forEach { restaurante ->
                                val idLong = restaurante.id?.toLong()
                                if (idLong != null) {
                                    val isSelected = selectedRestaurantIds.contains(idLong)

                                    RestaurantSelectableItem(
                                        restaurant = restaurante,
                                        selected = isSelected,
                                        enabled = isSelected || selectedRestaurantIds.size < MAX_RESTAURANTS,
                                        onToggle = {
                                            selectionMsg = null
                                            selectedRestaurantIds =
                                                if (isSelected) {
                                                    selectedRestaurantIds - idLong
                                                } else {
                                                    if (selectedRestaurantIds.size >= MAX_RESTAURANTS) {
                                                        selectionMsg = "Solo puedes seleccionar $MAX_RESTAURANTS restaurantes."
                                                        selectedRestaurantIds
                                                    } else {
                                                        selectedRestaurantIds + idLong
                                                    }
                                                }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Mensajes de validación de selección
                if (selectionMsg != null) {
                    Text(
                        text = selectionMsg!!,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Mensajes de error del registro
                if (registerState.success == false) {
                    Text(
                        text = "No se pudo registrar la ruta (puede que ya exista una con ese nombre).",
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if (registerState.error != null) {
                    Text(
                        text = "Error: ${registerState.error}",
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón Guardar ruta
                Button(
                    onClick = {
                        val ids = selectedRestaurantIds.toList()

                        if (ids.size != MAX_RESTAURANTS) {
                            selectionMsg =
                                "Debes seleccionar exactamente $MAX_RESTAURANTS restaurantes antes de guardar."
                            return@Button
                        }

                        selectionMsg = null
                        rutaVm.registrarConRestaurantes(
                            nombre = nombreRuta,
                            descripcion = if (descripcion.isBlank()) null else descripcion,
                            restaurantIds = ids
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !registerState.loading
                            && nombreRuta.isNotBlank()
                            && selectedRestaurantIds.size == MAX_RESTAURANTS
                ) {
                    Text(text = if (registerState.loading) "Guardando..." else "Guardar ruta")
                }

                // Botón Volver
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

/**
 * Ítem de restaurant con checkbox para selección
 */
@Composable
fun RestaurantSelectableItem(
    restaurant: RestaurantDto,
    selected: Boolean,
    enabled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onToggle() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = restaurant.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = restaurant.direccionText
                        ?: restaurant.ubicacion
                        ?: "Dirección no especificada",
                    style = MaterialTheme.typography.labelMedium
                )
                if (!restaurant.descripcion.isNullOrBlank()) {
                    Text(
                        text = restaurant.descripcion,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Checkbox(
                checked = selected,
                onCheckedChange = { if (enabled) onToggle() },
                enabled = enabled
            )
        }
    }
}

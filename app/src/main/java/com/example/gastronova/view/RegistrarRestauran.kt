package com.example.gastronova.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.gastronova.view.components.CampoAdjuntarImagen
import com.example.gastronova.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarRestaurant(navController: NavHostController) {
    var nombreRestaurant by remember { mutableStateOf("") }
    var empresa by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var tipoRestaurant by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf<Uri?>(null) }

    // Para el menu de seleccion
    val tipos=listOf("Italiano","Mexicano","Japones","Asiatico","China","Peruana","Molecular","Argentia","Vegana")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.gato_mapa),
            contentDescription = "GatoMapa",
            modifier = Modifier
                .align(Alignment.Center).padding(start = 110.dp).size(125.dp),
            contentScale = ContentScale.Fit

        )
    }
    Box(modifier = Modifier.fillMaxSize().padding(top = 125.dp).navigationBarsPadding()){
        Card(modifier = Modifier.align(Alignment.TopCenter)
            .padding(16.dp)
            .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Registrar restaurant",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align (Alignment.CenterHorizontally)
                )
                //NOMBRE DEL RESTAURANTE
                OutlinedTextField(
                    value = nombreRestaurant,
                    onValueChange = { nombreRestaurant = it },
                    label = { Text("Nombre del restaurante") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //NOMBRE DE LA EMPRESA
                OutlinedTextField(
                    value = empresa,
                    onValueChange = { empresa = it },
                    label = { Text("Nombre de la empresa") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //DIRECCION DEL RESTAURANT
                OutlinedTextField(
                    value = ubicacion,
                    onValueChange = { ubicacion = it },
                    label = { Text("Dirección del restaurante") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //TIPO DE RESTAURANTE
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f)
                ) {
                    OutlinedTextField(
                        value = tipoRestaurant,
                        onValueChange = { },              // readOnly
                        readOnly = true,
                        label = { Text("Tipo de restaurant") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                        },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        tipos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    tipoRestaurant = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                //DESCRIPCION
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion del restaurant") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                //IMAGEN
                CampoAdjuntarImagen(
                    imagenUri = imagen,
                    onImagenSeleccionada = { imagen = it }
                )

                Button(
                    onClick = { navController.navigate("Opcion")},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Registrar restaurante")
                }

            }
        }
    }
}

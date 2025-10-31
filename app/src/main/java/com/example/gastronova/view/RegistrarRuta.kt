package com.example.gastronova.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.gastronova.R
import com.example.gastronova.view.components.Selector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarRuta(navController: NavHostController) {
    var nombreRuta by remember { mutableStateOf("") }
    var tipoRuta by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var restaurante1 by remember { mutableStateOf("") }
    var restaurante2 by remember { mutableStateOf("") }
    var restaurante3 by remember { mutableStateOf("") }
    var restaurante4 by remember { mutableStateOf("") }
    var restaurante5 by remember { mutableStateOf("") }

    // Para el menu de seleccion
    val tipos = listOf("Tematica unica", "Tematica mixta")
    val restaurantes = listOf(
        "La gotita",
        "La nonna",
        "Golfo di napoli",
        "Kyoko Sushi",
        "Sushi supremo",
        "Kintaro ramen",
        "El japones",
        "Domino",
        "El palacio de los porotos",
        "Roka kiosko",
        "El rincon"
    )

    Scaffold(
        // hace que respete status + nav bars
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.gato_mapa),
                contentDescription = "GatoMapa",
                modifier = Modifier
                    .align(Alignment.Center).padding(start = 110.dp).size(125.dp),
                contentScale = ContentScale.Fit

            )
        }
        Box(modifier = Modifier.fillMaxSize().padding(top = 125.dp).navigationBarsPadding()) {
            Card(
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(16.dp)
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Registrar ruta gastronomica",
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    //NOMBRE DEL RESTAURANTE
                    OutlinedTextField(
                        value = nombreRuta,
                        onValueChange = { nombreRuta = it },
                        label = { Text("Nombre de la ruta") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )
                    // Tipo de ruta
                    Selector(
                        label = "Tipo de ruta",
                        value = tipoRuta,
                        options = tipos,
                        onSelect = { tipoRuta = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    Selector(
                        label = "Restaurante 1",
                        value = restaurante1,
                        options = restaurantes,
                        onSelect = { restaurante1 = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    Selector(
                        label = "Restaurante 2",
                        value = restaurante2,
                        options = restaurantes,
                        onSelect = { restaurante2 = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    Selector(
                        label = "Restaurante 3",
                        value = restaurante3,
                        options = restaurantes,
                        onSelect = { restaurante3 = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    Selector(
                        label = "Restaurante 4",
                        value = restaurante4,
                        options = restaurantes,
                        onSelect = { restaurante4 = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    Selector(
                        label = "Restaurante 5",
                        value = restaurante5,
                        options = restaurantes,
                        onSelect = { restaurante5 = it },
                        modifier = Modifier.fillMaxWidth().zIndex(1f)
                    )
                    //DESCRIPCION
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripcion de ruta") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )


                    Button(
                        onClick = { navController.navigate("Opcion") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Registrar ruta")
                    }

                }
            }
        }
    }
}

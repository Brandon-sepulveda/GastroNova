package com.example.gastronova

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gastronova.ui.theme.GastroNovaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GastroNovaTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}




@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {Login(navController)}
        composable("register") {Register(navController)}
        composable ("Opcion"){Opcion(navController)  }
        composable ("RegistrarRestaurant"){RegistrarRestaurant(navController)  }
        composable ("RegistrarRuta"){RegistrarRuta(navController)  }


    }}

//-----------LOGIN----------------
@Composable
fun Login(navController: NavHostController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(
                    text = "Bienvenido a GastroNavi",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id=R.drawable.ic_launcher_foreground_cat),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(0.45f),
                    contentScale = ContentScale.Fit
                )
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Button(
                    onClick = { navController.navigate("Opcion") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Iniciar Sesión")
                }
                Text(
                    text = "¿No tienes una cuenta?",
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { navController.navigate("register") },
                )

            }
        }
    }
}
//-----------REGISTER----------------
@Composable
fun Register(navController: NavHostController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repContrasena by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(
                    text = "Bienvenido a GastroNova",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                //NOMBRE
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //APELLIDO
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //CORREO
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                //USUARIO
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //CONTRASEÑA
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                //REPETIR CONTRASEÑA
                OutlinedTextField(
                    value = repContrasena,
                    onValueChange = { repContrasena = it },
                    label = { Text("Repetir contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Button(
                    onClick = { /* Acción al hacer clic en el botón */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Registrarse")
                }
                Text(
                    text = "¿Ya tienes una cuenta?",
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { navController.navigate("login") }
                )

            }
        }
    }
}

@Composable
fun Opcion(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    Box(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground_cat),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center).padding(start = 11.dp),
            contentScale = ContentScale.Fit

        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(top= 16.dp, start = 16.dp,end=16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(
                    text = "Bienvenido $nombre $apellido",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )


                Button(
                    onClick = { navController.navigate("RegistrarRestaurant") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Registrar restaurant")
                }
                Button(
                    onClick = { navController.navigate("RegistrarRuta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Registrar ruta gastronomica")
                }
                Button(
                    onClick = { /* Acción al hacer clic en el botón */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Ver rutas gastronomicas")
                }


                Image(
                    painter = painterResource(id=R.drawable.borde_inferior),
                    contentDescription = "BordeInferior",
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .fillMaxWidth().width(100.dp),
                    contentScale = ContentScale.Fit
                )

            }
        }
    }
}
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
    Box(modifier = Modifier.fillMaxSize().padding(top = 125.dp)){
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
    val tipos=listOf("Tematica unica","Tematica mixta")
    val restaurantes=listOf("La gotita","La nonna","Golfo di napoli","Kyoko Sushi","Sushi supremo","Kintaro ramen","El japones","Domino","El palacio de los porotos","Roka kiosko","El rincon")

    Box(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.gato_mapa),
            contentDescription = "GatoMapa",
            modifier = Modifier
                .align(Alignment.Center).padding(start = 110.dp).size(125.dp),
            contentScale = ContentScale.Fit

        )
    }
    Box(modifier = Modifier.fillMaxSize().padding(top = 125.dp)){
        Card(modifier = Modifier.align(Alignment.TopCenter)
            .padding(16.dp)
            .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Registrar ruta gastronomica",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align (Alignment.CenterHorizontally)
                )
                //NOMBRE DEL RESTAURANTE
                OutlinedTextField(
                    value = nombreRuta,
                    onValueChange = { nombreRuta = it },
                    label = { Text("Nombre del restaurante") },
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
                    onClick = { navController.navigate("Opcion")},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Registrar ruta")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Selector(
    label: String,
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { },        // readOnly
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
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
            options.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSelect(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CampoAdjuntarImagen(
    imagenUri: Uri?,
    onImagenSeleccionada: (Uri?) -> Unit
) {
    // Lanzador del Photo Picker (Android 13+; en 33+ no requiere permisos)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        onImagenSeleccionada(uri) // guarda el Uri seleccionado (o null si canceló)
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(
            onClick = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adjuntar imagen")
        }

        // Vista previa (opcional): miniatura cuando hay imagen seleccionada
        if (imagenUri != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = imagenUri,
                    contentDescription = "Vista previa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        Register(navController)
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        Login(navController)
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OpcionScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        Opcion(navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrarRestaurantScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        RegistrarRestaurant(navController)
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrarRutaScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        RegistrarRuta(navController)
    }
}
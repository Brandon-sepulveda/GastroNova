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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.sp
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
        composable ("VerRuta"){VerRutas(navController)  }


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
                    onClick = { navController.navigate("VerRuta") },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerRutas(navController: NavHostController) {
    var ruta by remember { mutableStateOf("") }
    var tipoRuta by remember { mutableStateOf("") }

    // Tipos de temática para el menú de selección
    val tipos = listOf("Tematica unica", "Tematica mixta")

    // Rutas gastronómicas disponibles
    val rutas = listOf(
        "Ruta del ramen",
        "Ruta del completo",
        "Ruta a la italiana",
        "Ruta del sushi",
        "La gran ruta peruana",
        "Ruta del chocolate",
    )

    // ===============================
    //   Restaurantes por cada ruta
    // ===============================

    // Ruta del ramen
    val ramenKintaro = listOf(
        "Ramen Kintaro",
        "Monjitas 460, Santiago",
        "Uno de los pioneros especializados en ramen auténtico en Santiago. Ofrecen variedades de caldo como miso, shio, shoyu y tonkotsu."
    )
    val ramenRyoma = listOf(
        "Ramen Ryoma Providencia",
        "General Holley 2312, Providencia",
        "Restaurante japonés especializado en ramen tradicional con diferentes caldos y toppings, reconocido por su sabor auténtico."
    )
    val ootoya = listOf(
        "Ootoya – Ramen & Noodles House",
        "Constitución 125, Providencia",
        "Restaurante japonés moderno que ofrece ramen artesanal y platos de fideos, ideal para disfrutar en un ambiente relajado y contemporáneo."
    )
    val isekaiRamen = listOf(
        "Isekai Ramen",
        "Girardi 1236, Providencia",
        "Pequeño local temático que mezcla cultura anime con ramen casero de excelente calidad. Recomendado para quienes buscan una experiencia distinta."
    )
    val ramenOne = listOf(
        "Ramen One Independencia",
        "Av. Independencia 565, Mall Barrio Independencia, Santiago",
        "Ramen con fideos artesanales y recetas clásicas japonesas, ideal para quienes buscan buena calidad a precios accesibles."
    )
    val rutaRamen = listOf(ramenKintaro, ramenRyoma, ootoya, isekaiRamen, ramenOne)

    // Ruta del completo
    val completo1 = listOf("El Rey del Completo","Centro de Santiago","Local tradicional chileno especializado en completos, sandwiches y papas fritas, ideal para una experiencia rápida y auténtica.")
    val completo2 = listOf("Dominó","Santiago, Región Metropolitana","Cadena dedicada al completo chileno, muy popular para comida rápida, sabor clásico y ambiente informal.")
    val completo3 = listOf("Quick Lunch Alemán","Santiago, Región Metropolitana","Bar de comida rápida con completos y otras opciones rápidas, buena alternativa para un almuerzo ágil.")
    val completo4 = listOf("El Completo.cl","Santiago, Región Metropolitana","Tienda física y online con variedad de ingredientes e innovaciones del completo clásico.")
    val completo5 = listOf("Pedro, Juan & Diego","Santiago, Región Metropolitana","Local que incluye completos en su carta junto a hamburguesas, ideal para quienes quieren explorar variantes del completo clásico.")
    val rutaCompleto = listOf(completo1, completo2, completo3, completo4, completo5)

    // Ruta a la italiana
    val italiana1 = listOf("Ristorante Sole Mio","Moneda 1816, Santiago","Restaurante italiano clásico con pastas artesanales y ambiente acogedor.")
    val italiana2 = listOf("Piegari Ristorante","Santiago, Región Metropolitana","Cocina italiana tradicional con recetas familiares y ambiente elegante.")
    val italiana3 = listOf("La Fabbrica","Av. Ossa 123, La Reina, Santiago","Auténtica gastronomía italiana con pizzas napolitanas y pastas caseras.")
    val italiana4 = listOf("Italian Trattoria","Constitución 270, Providencia","Trattoria con pizzas, risottos y pastas en barrio Providencia.")
    val italiana5 = listOf("Bottega Rivoli","Santiago, Región Metropolitana","Restaurante italiano enfocado en pasta fresca y buena selección de vinos.")
    val rutaItaliana = listOf(italiana1, italiana2, italiana3, italiana4, italiana5)

    // Ruta del sushi
    val sushi1 = listOf("Matsuri","Hotel Mandarin Oriental, Las Condes","Restaurante de sushi de alta gama con toques nikkei y ambiente refinado.")
    val sushi2 = listOf("Osaka – Santiago","Av. Nueva Costanera 3736B, Vitacura","Experiencia de sushi nikkei con fusión peruana y japonesa.")
    val sushi3 = listOf("Do Sushi","Santiago, Región Metropolitana","Restaurante artesanal con rolls originales y excelente presentación.")
    val sushi4 = listOf("Kyoko Sushi","Amunátegui 646, Santiago / Av. Providencia 2571","Cadena con rolls variados, buen precio y sabor equilibrado.")
    val sushi5 = listOf("Niu Sushi","Santiago, Región Metropolitana","Cadena popular con gran variedad de rolls y combinaciones de sabores.")
    val rutaSushi = listOf(sushi1, sushi2, sushi3, sushi4, sushi5)

    // La gran ruta peruana
    val peruana1 = listOf("La Mar Santiago","Nueva Costanera, Vitacura","Cocina peruana de mariscos inspirada en las cebicherías tradicionales del Perú.")
    val peruana2 = listOf("Zarita Restaurant Boutique","Santiago Centro","Especializado en comida peruana arequipeña con recetas criollas auténticas.")
    val peruana3 = listOf("El Gusto Peruano","Santiago Centro","Cevichería con ambiente casual y excelente relación precio/calidad.")
    val peruana4 = listOf("Sabores Aji Seco","Santiago Centro","Platos criollos y marinos peruanos en porciones abundantes.")
    val peruana5 = listOf("Sazón Peruana","Santiago Centro","Comida peruana tradicional con platos emblemáticos como lomo saltado y ají de gallina.")
    val rutaPeruana = listOf(peruana1, peruana2, peruana3, peruana4, peruana5)

    // Ruta del chocolate
    val chocolate1 = listOf("Brussels Heart of Chocolate","Santa Magdalena 187, Providencia","Chocolatería gourmet con bombones, tortas y productos belgas premium.")
    val chocolate2 = listOf("Club Chocolate","Ernesto Pinto Lagarrigue 192, Bellavista","Café-bar con postres y cocteles basados en chocolate artesanal.")
    val chocolate3 = listOf("ChocolateCafé","Santiago Centro","Cafetería especializada en bebidas de cacao y repostería casera.")
    val chocolate4 = listOf("La Piccola Italia Dulce","Paseo Ahumada 387, Santiago","Cafetería italiana con chocolates, cannolis y postres típicos.")
    val chocolate5 = listOf("Club del Chocolate","Santiago, Región Metropolitana","Espacio gourmet para amantes del cacao con degustaciones y productos selectos.")
    val rutaChocolate = listOf(chocolate1, chocolate2, chocolate3, chocolate4, chocolate5)

    // ===================================
    //     Lista global de todas las rutas
    // ===================================
    val rutasGastronomicas = listOf(
        listOf("Ruta del ramen", "Explora los mejores ramen de Santiago, con caldos intensos y fideos artesanales en distintos estilos japoneses.", rutaRamen),
        listOf("Ruta del completo", "Descubre los lugares más emblemáticos para probar el clásico completo chileno en sus distintas versiones.", rutaCompleto),
        listOf("Ruta a la italiana", "Un recorrido por las mejores trattorias y ristorantes italianos de la Región Metropolitana.", rutaItaliana),
        listOf("Ruta del sushi", "Una experiencia japonesa en Santiago con los rolls más frescos y creativos de la ciudad.", rutaSushi),
        listOf("La gran ruta peruana", "Viaja por los sabores del Perú sin salir de Santiago, con cebiches, lomo saltado y ajíes criollos.", rutaPeruana),
        listOf("Ruta del chocolate", "Endulza el recorrido con los mejores lugares dedicados al chocolate artesanal y gourmet en Santiago.", rutaChocolate)
    )

    // Derivar la ruta seleccionada
    val (nombreRutaSel, descripcionRutaSel, restaurantesSel) = remember(ruta) {
        val selected = rutasGastronomicas.firstOrNull { it.getOrNull(0) == ruta }
        val nombre = selected?.getOrNull(0) as? String ?: ""
        val desc = selected?.getOrNull(1) as? String ?: ""
        @Suppress("UNCHECKED_CAST")
        val rests = selected?.getOrNull(2) as? List<List<String>> ?: emptyList()
        Triple(nombre, desc, rests)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
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

                    // Tipo de ruta (no filtra por ahora, pero queda para usarlo después)
                    Selector(
                        label = "Tipo de ruta",
                        value = tipoRuta,
                        options = tipos,
                        onSelect = { tipoRuta = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(1f)
                    )

                    // Selección de ruta
                    Selector(
                        label = "Ruta",
                        value = ruta,
                        options = rutas,
                        onSelect = { ruta = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(1f)
                    )

                    // --- Sección de resultado ---
                    if (ruta.isNotBlank() && nombreRutaSel.isNotBlank()) {
                        Divider(modifier = Modifier.height(3.dp))
                        Text(
                            text = nombreRutaSel,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        // Descripción de la ruta (al final)
                        Text(
                            text = descripcionRutaSel,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Divider(modifier = Modifier.height(3.dp))

                        // Lista de restaurantes (Nombre, Dirección, Descripción)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            restaurantesSel.forEach { rest ->
                                // rest[0] = nombre, rest[1] = dirección, rest[2] = descripción
                                RestaurantItem(
                                    nombre = rest.getOrNull(0).orEmpty(),
                                    direccion = rest.getOrNull(1).orEmpty(),
                                    descripcion = rest.getOrNull(2).orEmpty()
                                )
                                Divider()
                            }
                        }


                    }

                    // Acción opcional
                    Button(
                        onClick = { navController.navigate("Opcion") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(text = "Guardar ruta")
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantItem(
    nombre: String,
    direccion: String,
    descripcion: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = nombre,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = direccion,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodySmall
        )
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun VerRutasScreenPreview() {
    val navController = rememberNavController()
    GastroNovaTheme {
        VerRutas(navController)
    }
}
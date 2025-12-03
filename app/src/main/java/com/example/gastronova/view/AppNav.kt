package com.example.gastronova.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        // Login y registro
        composable("login") { Login(navController) }
        composable("register") { RegistrarUsuario(navController) }

        // Homes según tipo_usuario
        composable("homeAdmin") { HomeAdmin(navController) }
        composable("homeUser") { HomeUser(navController) }

        // Resto de pantallas
        composable("RegistrarRestaurant") { RegistrarRestaurant(navController) }
        composable("RegistrarRuta") { RegistrarRuta(navController) }
        composable("VerRutas") { VerRutas(navController) }
        composable("RutasGuardadas") { RutasGuardadas(navController) }
        composable("VerRutasUsuario") { VerRutasUsuario(navController) }
    }
}

package com.example.gastronova.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNav(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {Login(navController)}
        composable("register") {Register(navController)}
        composable ("Opcion"){Opcion(navController)  }
        composable ("RegistrarRestaurant"){RegistrarRestaurant(navController)  }
        composable ("RegistrarRuta"){RegistrarRuta(navController)  }
        composable ("VerRuta"){VerRutas(navController)  }


    }}
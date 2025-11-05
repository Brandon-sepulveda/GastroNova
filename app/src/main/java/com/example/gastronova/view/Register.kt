package com.example.gastronova.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gastronova.R
import com.example.gastronova.controller.AuthViewModel

//-----------REGISTER----------------
@Composable
fun Register(navController: NavHostController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repContrasena by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var showPass2 by rememberSaveable { mutableStateOf(false) }

    val vm: AuthViewModel = viewModel()
    val state by vm.registerState.collectAsState()
    var showAlert by remember { mutableStateOf(false) }

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
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(
                                painter = painterResource(
                                    if (showPass) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                ),
                                contentDescription = if (showPass) "Ocultar" else "Mostrar",
                                tint = Color.Unspecified
                            )
                        }
                    }
                )
                //REPETIR CONTRASEÑA
                OutlinedTextField(
                    value = repContrasena,
                    onValueChange = { repContrasena = it },
                    label = { Text("Repetir contraseña") },
                    visualTransformation = if (showPass2) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        IconButton(onClick = { showPass2 = !showPass2 }) {
                            Icon(
                                painter = painterResource(
                                    if (showPass2) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                ),
                                contentDescription = if (showPass2) "Ocultar" else "Mostrar",
                                tint = Color.Unspecified
                            )
                        }
                    }
                )

                Button(
                    onClick = {
                        if (contrasena != repContrasena) {
                            showAlert = true
                        } else {
                            vm.registrar(
                                nombre = nombre.trim(),
                                apellido = apellido.trim(),
                                correo = correo.trim(),
                                usuario = usuario.trim(),
                                contrasena = contrasena.trim()
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 2.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = "Registrarse")
                    }
                }
                if (showAlert) {
                    AlertDialog(
                        onDismissRequest = { showAlert = false },
                        confirmButton = {
                            TextButton(onClick = { showAlert = false }) {
                                Text("Entendido")
                            }
                        },
                        title = { Text("Contraseñas no coinciden") },
                        text = { Text("Por favor asegúrate de que ambas contraseñas sean iguales antes de continuar.") }
                    )
                }
                when {
                    state.loading -> Text("Creando cuenta...")
                    state.success == true -> {
                        // vuelve al login
                        LaunchedEffect(Unit) { navController.popBackStack() }
                    }
                    state.success == false -> Text("No se pudo registrar (usuario o correo ya existen)")
                    state.error != null -> Text("Error: ${state.error}")
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
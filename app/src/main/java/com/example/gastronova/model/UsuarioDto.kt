package com.example.gastronova.model

import com.google.gson.annotations.SerializedName

data class UsuarioDto(
    val id: Int? = null,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val usuario: String,
    val contrasena: String,

    @SerializedName("tipo_usuario") // <- el backend sigue usando tipo_usuario
    val tipoUsuario: Boolean = true // <- en Kotlin usamos camelCase
)

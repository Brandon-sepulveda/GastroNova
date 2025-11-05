package com.example.gastronova.model

data class UsuarioDto(
    val id: Int? = null,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val usuario: String,
    val contrasena: String,
    val tipo_usuario: Boolean = true
)
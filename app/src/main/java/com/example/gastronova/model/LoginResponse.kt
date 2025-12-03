package com.example.gastronova.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val success: Boolean,
    val id: Int?,
    val nombre: String?,
    val apellido: String?,
    val correo: String?,
    val usuario: String?,

    @SerializedName("tipo_usuario")
    val tipoUsuario: Boolean? = false
)

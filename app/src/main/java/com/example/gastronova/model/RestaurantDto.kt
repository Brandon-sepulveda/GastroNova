package com.example.gastronova.model

data class RestaurantDto(
    val id: Long? = null,
    val nombre: String,
    val empresa: String? = null,
    val ubicacion: String? = null,
    val tipo: String? = null,
    val descripcion: String? = null,
    val direccionText: String? = null   // viene desde el backend
)

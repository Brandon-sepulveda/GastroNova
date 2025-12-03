package com.example.gastronova.model

data class RutaCreateRequest(
    val nombre: String,
    val descripcion: String?,
    val restaurantIds: List<Long>
)

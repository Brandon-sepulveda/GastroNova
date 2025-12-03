package com.example.gastronova.model

data class RutaDto(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String? = null,
    val restaurantes: List<RestaurantDto> = emptyList()
)

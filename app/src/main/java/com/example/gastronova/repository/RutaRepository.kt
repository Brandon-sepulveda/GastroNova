package com.example.gastronova.repository

import com.example.gastronova.api.RutaApi
import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.network.ApiClient

class RutaRepository(
    private val api: RutaApi = ApiClient.rutaApi
) {

    suspend fun registrarAntiguo(dto: RutaDto): Result<Boolean> {
        val request = RutaCreateRequest(
            nombre = dto.nombre,
            descripcion = dto.descripcion,
            restaurantIds = dto.restaurantes.mapNotNull { it.id }
        )
        return registrarConRestaurantes(request)
    }

    suspend fun registrarConRestaurantes(request: RutaCreateRequest): Result<Boolean> = runCatching {
        val response = api.registrar(request)

        if (response.isSuccessful) {
            true
        } else {
            val err = response.errorBody()?.string()
            throw Exception("Error HTTP ${response.code()}${if (!err.isNullOrBlank()) ": $err" else ""}")
        }
    }

    suspend fun listar(): Result<List<RutaDto>> = runCatching {
        val response = api.listar()
        if (response.isSuccessful) response.body().orEmpty()
        else throw Exception("Error HTTP ${response.code()}: ${response.errorBody()?.string() ?: "Error desconocido"}")
    }
}

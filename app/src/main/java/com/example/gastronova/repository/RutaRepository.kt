package com.example.gastronova.repository

import com.example.gastronova.api.RutaApi
import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.network.ApiClient   // 👈 USAMOS ApiClient, NO RetrofitClient

class RutaRepository(
    private val api: RutaApi = ApiClient.rutaApi
) {

    // Versión antigua: por si en algún lado aún usas registrar con RutaDto
    suspend fun registrarAntiguo(dto: RutaDto): Result<Boolean> {
        return try {
            val request = RutaCreateRequest(
                nombre = dto.nombre,
                descripcion = dto.descripcion,
                restaurantIds = emptyList() // sin restaurantes asociados
            )
            val response = api.registrar(request)
            if (response.isSuccessful) {
                Result.success(response.body() ?: false)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Nueva versión: ruta con restaurantes
    suspend fun registrarConRestaurantes(request: RutaCreateRequest): Result<Boolean> {
        return try {
            val response = api.registrar(request)
            if (response.isSuccessful) {
                Result.success(response.body() ?: false)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listar(): Result<List<RutaDto>> {
        return try {
            val response = api.listar()
            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

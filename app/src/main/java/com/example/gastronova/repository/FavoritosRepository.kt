package com.example.gastronova.repository

import com.example.gastronova.api.FavoritosApi
import com.example.gastronova.model.FavoritoRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.network.ApiClient

class FavoritosRepository(
    private val api: FavoritosApi = ApiClient.favoritosApi
) {
    suspend fun guardar(usuarioId: Int, rutaId: Int): Result<Boolean> = runCatching {
        val r = api.guardar(FavoritoRequest(usuarioId, rutaId))

        if (r.isSuccessful) {
            true
        } else {
            when (r.code()) {
                409 -> error("La ruta ya estaba guardada")
                400 -> error("Solicitud inválida al guardar")
                else -> error("HTTP ${r.code()}: ${r.errorBody()?.string() ?: "Error desconocido"}")
            }
        }
    }

    suspend fun listar(usuarioId: Int): Result<List<RutaDto>> = runCatching {
        val r = api.listar(usuarioId)
        if (r.isSuccessful) r.body() ?: emptyList()
        else error("HTTP ${r.code()}: ${r.errorBody()?.string() ?: "Error desconocido"}")
    }

    suspend fun eliminar(usuarioId: Int, rutaId: Int): Result<Boolean> = runCatching {
        val r = api.eliminar(FavoritoRequest(usuarioId, rutaId))

        if (r.isSuccessful) {
            true
        } else {
            when (r.code()) {
                404 -> error("La ruta no estaba guardada")
                400 -> error("Solicitud inválida al eliminar")
                else -> error("HTTP ${r.code()}: ${r.errorBody()?.string() ?: "Error desconocido"}")
            }
        }
    }
}

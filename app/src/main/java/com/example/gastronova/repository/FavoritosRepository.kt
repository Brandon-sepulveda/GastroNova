package com.example.gastronova.repository

import com.example.gastronova.api.FavoritosApi
import com.example.gastronova.model.FavoritoRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.network.ApiClient

class FavoritosRepository(
    private val api: FavoritosApi = ApiClient.favoritosApi
) {


    suspend fun guardarFavorito(usuarioId: Int, rutaId: Int): Result<Unit> {
        return try {
            val res = api.guardar(FavoritoRequest(usuarioId, rutaId))

            when (res.code()) {
                200, 201, 204 -> Result.success(Unit)
                409 -> Result.failure(IllegalStateException("Esa ruta ya está en favoritos"))
                400 -> Result.failure(IllegalArgumentException("Solicitud inválida al guardar"))
                else -> Result.failure(RuntimeException("HTTP ${res.code()} al guardar favorito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarFavoritos(usuarioId: Int): Result<List<RutaDto>> {
        return try {
            val res = api.listar(usuarioId)
            if (res.isSuccessful) {
                Result.success(res.body() ?: emptyList())
            } else {
                // si tu backend a veces devuelve 400 sin datos, lo tratamos como lista vacía
                if (res.code() == 400) Result.success(emptyList())
                else Result.failure(RuntimeException("HTTP ${res.code()} al listar favoritos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarFavorito(usuarioId: Int, rutaId: Int): Result<Unit> {
        return try {
            val res = api.eliminar(FavoritoRequest(usuarioId, rutaId))
            when (res.code()) {
                200, 204 -> Result.success(Unit)
                404 -> Result.failure(NoSuchElementException("No se encontró ese favorito"))
                400 -> Result.failure(IllegalArgumentException("Solicitud inválida al eliminar"))
                else -> Result.failure(RuntimeException("HTTP ${res.code()} al eliminar favorito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =========================
    //  Métodos usados por FavoritosViewModel (guardar/listar/eliminar)
    // =========================
    suspend fun guardar(usuarioId: Int, rutaId: Int): Result<Boolean> {
        return guardarFavorito(usuarioId, rutaId).fold(
            onSuccess = { Result.success(true) },
            onFailure = { Result.failure(it) }
        )
    }

    suspend fun listar(usuarioId: Int): Result<List<RutaDto>> {
        return listarFavoritos(usuarioId)
    }

    suspend fun eliminar(usuarioId: Int, rutaId: Int): Result<Boolean> {
        return eliminarFavorito(usuarioId, rutaId).fold(
            onSuccess = { Result.success(true) },
            onFailure = { Result.failure(it) }
        )
    }
}

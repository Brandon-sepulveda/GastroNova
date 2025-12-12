package com.example.gastronova.repository

import com.example.gastronova.api.FavoritosApi
import com.example.gastronova.model.FavoritoRequest
import com.example.gastronova.model.RutaDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class FavoritosRepositoryTest {
    private val api: FavoritosApi = mockk()
    private val repo = FavoritosRepository(api)

    @Test
    fun `guardarFavorito - HTTP 409 - lanza IllegalStateException`() = runTest {
        val errorBody = "conflict".toResponseBody("text/plain".toMediaType())
        coEvery { api.guardar(FavoritoRequest(1, 99)) } returns Response.error(409, errorBody)

        val result = repo.guardarFavorito(usuarioId = 1, rutaId = 99)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
        assertEquals("Esa ruta ya está en favoritos", result.exceptionOrNull()!!.message)
    }

    @Test
    fun `listarFavoritos - error HTTP 400 - devuelve lista vacía`()= runTest {
        val errorBody = "bad request".toResponseBody("text/plain".toMediaType())
        coEvery { api.listar(1) } returns Response.error(400, errorBody)

        val result = repo.listarFavoritos(usuarioId = 1)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }

    @Test
    fun `eliminarFavorito - HTTP 404 - lanza NoSuchElementException`() = runTest {
        val errorBody = "not found".toResponseBody("text/plain".toMediaType())
        coEvery { api.eliminar(FavoritoRequest(1, 99)) } returns Response.error(404, errorBody)

        val result = repo.eliminarFavorito(usuarioId = 1, rutaId = 99)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)
        assertEquals("No se encontró ese favorito", result.exceptionOrNull()!!.message)
    }

    @Test
    fun `listarFavoritos - exito - devuelve lista de favoritos`() = runTest {
        val data = listOf(
            RutaDto(id = 1, nombre = "Ruta 1"),
            RutaDto(id = 2, nombre = "Ruta 2")
        )
        coEvery { api.listar(1) } returns Response.success(data)

        val result = repo.listarFavoritos(1)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.size)
    }
}

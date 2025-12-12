package com.example.gastronova.repository

import com.example.gastronova.api.RutaApi
import com.example.gastronova.model.RestaurantDto
import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class RutaRepositoryTest {

    private val api: RutaApi = mockk()
    private val repo = RutaRepository(api)

    @Test
    fun `registrarConRestaurantes - exito - devuelve true`() = runTest {
        val req = RutaCreateRequest(
            nombre = "Ruta del sushi",
            descripcion = "sushi top",
            restaurantIds = listOf(1, 2, 3, 4, 5)
        )
        coEvery { api.registrar(req) } returns Response.success(Unit)
        val result = repo.registrarConRestaurantes(req)
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `registrarConRestaurantes - error HTTP 400 - devuelve failure`() = runTest {
        val req = RutaCreateRequest(
            nombre = "Ruta mala",
            descripcion = null,
            restaurantIds = listOf(1, 2, 3, 4, 5)
        )

        val errorBody = """{"msg":"bad request"}"""
            .toResponseBody("application/json".toMediaType())

        coEvery { api.registrar(req) } returns Response.error(400, errorBody)

        val result = repo.registrarConRestaurantes(req)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("Error HTTP 400"))
        assertTrue(result.exceptionOrNull()!!.message!!.contains("bad request"))
    }

    @Test
    fun `registrarAntiguo - mapea ids válidos - llama a API correctamente`() = runTest {
        val dto = RutaDto(
            nombre = "Ruta antigua",
            descripcion = "desc",
            restaurantes = listOf(
                RestaurantDto(id = 10, nombre = "A"),
                RestaurantDto(id = null, nombre = "B"),
                RestaurantDto(id = 30, nombre = "C")
            )
        )

        // aceptar cualquier request y responder OK
        coEvery { api.registrar(any()) } returns Response.success(Unit)

        val result = repo.registrarAntiguo(dto)

        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())

        coVerify {
            api.registrar(match {
                it.nombre == "Ruta antigua" &&
                        it.descripcion == "desc" &&
                        it.restaurantIds == listOf(10L, 30L)
            })
        }
    }
}

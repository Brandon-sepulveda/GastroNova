package com.example.gastronova.repository

import com.example.gastronova.api.RestaurantApi
import com.example.gastronova.model.RestaurantDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class RestaurantRepositoryTest {
    private val api: RestaurantApi = mockk()
    private val repo = RestaurantRepository(api)

    @Test
    fun `registrar - exito - devuelve true`()  = runTest {
        val dto = RestaurantDto(nombre = "Ramen Kintaro", descripcion = "rico")
        coEvery { api.registrar(dto) } returns Response.success(true)
        val result = repo.registrar(dto)
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `registrar - HTTP 409 - devuelve false`() = runTest {
        val dto = RestaurantDto(nombre = "Ramen Kintaro", descripcion = "rico")
        val errorBody = "conflict".toResponseBody("text/plain".toMediaType())
        coEvery { api.registrar(dto) } returns Response.error(409, errorBody)
        val result = repo.registrar(dto)
        assertTrue(result.isSuccess)
        assertEquals(false, result.getOrNull())
    }

    @Test
    fun `listar - error HTTP - devuelve failure`() = runTest {
        val errorBody = "server error".toResponseBody("text/plain".toMediaType())
        coEvery { api.listar() } returns Response.error(500, errorBody)
        val result = repo.listar()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("Error HTTP 500"))
    }
}

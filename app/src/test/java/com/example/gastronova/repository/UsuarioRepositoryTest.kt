package com.example.gastronova.repository

import com.example.gastronova.api.UsuarioApi
import com.example.gastronova.model.LoginRequest
import com.example.gastronova.model.LoginResponse
import com.example.gastronova.model.UsuarioDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class UsuarioRepositoryTest {

    private val api: UsuarioApi = mockk()
    private val repo = UsuarioRepository(api)

    @Test
    fun `registrar - exito - devuelve true`()  = runTest {
        val dto = UsuarioDto(
            nombre = "Ana",
            apellido = "Perez",
            correo = "ana@correo.com",
            usuario = "ana",
            contrasena = "1234",
            tipoUsuario = true
        )

        coEvery { api.registrar(dto) } returns Response.success(true)

        val result = repo.registrar(dto)

        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())
    }

    @Test
    fun `registrar - fallo - devuelve false`() = runTest {
        val dto = UsuarioDto(
            nombre = "Ana",
            apellido = "Perez",
            correo = "ana@correo.com",
            usuario = "ana",
            contrasena = "1234",
            tipoUsuario = true
        )

        coEvery { api.registrar(dto) } returns Response.success(false)

        val result = repo.registrar(dto)

        assertTrue(result.isSuccess)
        assertEquals(false, result.getOrNull())
    }

    @Test
    fun `login - exito - devuelve LoginResponse`()= runTest {
        val resp = LoginResponse(
            success = true,
            id = 7,
            nombre = "Ana",
            apellido = "Perez",
            correo = "ana@correo.com",
            usuario = "ana",
            tipoUsuario = true
        )

        coEvery { api.login(LoginRequest("ana", "1234")) } returns Response.success(resp)

        val result = repo.login("ana", "1234")

        assertTrue(result.isSuccess)
        assertEquals(7, result.getOrNull()!!.id)
        assertEquals("Ana", result.getOrNull()!!.nombre)
    }

    @Test
    fun `login - error HTTP - devuelve failure`() = runTest {
        val errorBody = """{"error":"unauthorized"}"""
            .toResponseBody("application/json".toMediaType())

        coEvery { api.login(any()) } returns Response.error(401, errorBody)

        val result = repo.login("ana", "mala")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()!!.message!!.contains("HTTP 401"))
    }
}

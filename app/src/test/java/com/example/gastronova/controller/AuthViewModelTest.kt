package com.example.gastronova.controller

import com.example.gastronova.model.LoginResponse
import com.example.gastronova.repository.UsuarioRepository
import com.example.gastronova.session.SessionManager
import com.example.gastronova.testutils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `login - inicio de sesion exitoso - actualiza SessionManager y estado`() = runTest {
        SessionManager.clear()

        val repo: UsuarioRepository = mockk()
        val response = LoginResponse(
            success = true,
            id = 7,
            nombre = "Ana",
            apellido = "Perez",
            correo = "ana@correo.com",
            usuario = "ana",
            tipoUsuario = true
        )

        coEvery { repo.login("ana", "1234") } returns Result.success(response)

        val vm = AuthViewModel(repo)
        vm.login("ana", "1234")

        advanceUntilIdle()

        assertEquals(true, vm.loginState.value.success)
        assertEquals(7, SessionManager.usuarioId)
        assertEquals("Ana", SessionManager.usuarioNombre)
        assertEquals("ana", SessionManager.usuarioUsuario)
        assertEquals(true, SessionManager.tipoUsuario)
    }

    @Test
    fun `login - inicio de sesion fallido - estado error usuario o contrasena`() = runTest {
        SessionManager.clear()

        val repo: UsuarioRepository = mockk()
        val response = LoginResponse(
            success = false,
            id = null,
            nombre = null,
            apellido = null,
            correo = null,
            usuario = null,
            tipoUsuario = false
        )

        coEvery { repo.login("ana", "mala") } returns Result.success(response)

        val vm = AuthViewModel(repo)
        vm.login("ana", "mala")

        advanceUntilIdle()

        assertEquals(false, vm.loginState.value.success)
        assertEquals("Usuario o contraseña incorrectos", vm.loginState.value.error)
        assertFalse(SessionManager.isLoggedIn())
    }
}

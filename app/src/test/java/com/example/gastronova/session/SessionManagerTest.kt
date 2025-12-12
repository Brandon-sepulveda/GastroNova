package com.example.gastronova.session

import org.junit.Assert.*
import org.junit.Test

class SessionManagerTest {

    @Test
    fun `clear - deja sesion vacia`() {
        SessionManager.usuarioId = 10
        SessionManager.usuarioNombre = "Ana"
        SessionManager.usuarioUsuario = "ana"
        SessionManager.tipoUsuario = true

        SessionManager.clear()

        assertNull(SessionManager.usuarioId)
        assertNull(SessionManager.usuarioNombre)
        assertNull(SessionManager.usuarioUsuario)
        assertNull(SessionManager.tipoUsuario)
        assertFalse(SessionManager.isLoggedIn())
        assertFalse(SessionManager.isAdmin())
    }

    @Test
    fun `isLoggedIn - true si hay usuarioId`() {
        SessionManager.clear()
        assertFalse(SessionManager.isLoggedIn())

        SessionManager.usuarioId = 1
        assertTrue(SessionManager.isLoggedIn())
    }

    @Test
    fun `isAdmin - true si tipoUsuario es true`() {
        SessionManager.clear()
        SessionManager.tipoUsuario = false
        assertFalse(SessionManager.isAdmin())

        SessionManager.tipoUsuario = true
        assertTrue(SessionManager.isAdmin())
    }
}

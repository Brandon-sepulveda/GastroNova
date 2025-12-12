package com.example.gastronova.controller

import com.example.gastronova.repository.FavoritosRepository
import com.example.gastronova.repository.RutaRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class RutaViewModelTest {

    @Test
    fun `registrarConRestaurantes - si no son 5 - setea error y NO llama repo`() {
        val repo: RutaRepository = mockk(relaxed = true)
        val favRepo: FavoritosRepository = mockk(relaxed = true)

        val vm = RutaViewModel(repo, favRepo)

        vm.registrarConRestaurantes(
            nombre = "Ruta",
            descripcion = "desc",
            restaurantIds = listOf(1, 2, 3, 4) // <-- 4 (incorrecto)
        )

        assertEquals("Debes seleccionar exactamente 5 restaurantes.", vm.registerState.value.error)

        coVerify(exactly = 0) { repo.registrarConRestaurantes(any()) }
    }

    @Test
    fun `guardarRutaSeleccionadaEnFavoritos - si no hay seleccion - mensaje`() {
        val repo: RutaRepository = mockk(relaxed = true)
        val favRepo: FavoritosRepository = mockk(relaxed = true)

        val vm = RutaViewModel(repo, favRepo)

        // no seleccionamos ruta
        vm.guardarRutaSeleccionadaEnFavoritos(usuarioId = 1)

        assertEquals("Debes seleccionar una ruta", vm.uiMessage.value)
    }
}

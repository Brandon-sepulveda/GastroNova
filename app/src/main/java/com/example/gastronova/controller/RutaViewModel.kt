package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.repository.RutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RutaListState(
    val loading: Boolean = false,
    val data: List<RutaDto> = emptyList(),
    val error: String? = null
)

/**
 * SimpleUiState ya lo tenías creado y usado en otros ViewModel.
 * Lo seguimos reutilizando tal cual.
 */
class RutaViewModel(
    private val repo: RutaRepository = RutaRepository()
) : ViewModel() {

    private val _registerState = MutableStateFlow(SimpleUiState())
    val registerState: StateFlow<SimpleUiState> = _registerState

    private val _listState = MutableStateFlow(RutaListState())
    val listState: StateFlow<RutaListState> = _listState

    /**
     * Método antiguo: lo dejamos pero ahora delega a la nueva versión con
     * lista vacía de restaurantIds.
     */
    fun registrar(nombre: String, descripcion: String?) {
        registrarConRestaurantes(
            nombre = nombre,
            descripcion = descripcion,
            restaurantIds = emptyList()
        )
    }

    /**
     * NUEVO: registra la ruta y asocia los restaurantes (por id).
     * Esto es lo que usas desde la nueva view RegistrarRuta.
     */
    fun registrarConRestaurantes(
        nombre: String,
        descripcion: String?,
        restaurantIds: List<Long>
    ) {
        // Validación: la ruta debe tener EXACTAMENTE 5 restaurantes
        if (restaurantIds.size != 5) {
            _registerState.value = SimpleUiState(error = "Debes seleccionar exactamente 5 restaurantes.")
            return
        }

        _registerState.value = SimpleUiState(loading = true)
        viewModelScope.launch {
            val request = RutaCreateRequest(
                nombre = nombre,
                descripcion = descripcion,
                restaurantIds = restaurantIds
            )

            val res = repo.registrarConRestaurantes(request)
            _registerState.value = res.fold(
                onSuccess = { ok -> SimpleUiState(success = ok) },
                onFailure = { e -> SimpleUiState(error = e.message ?: "Error desconocido") }
            )
        }
    }

    fun cargarRutas() {
        _listState.value = RutaListState(loading = true)
        viewModelScope.launch {
            val res = repo.listar()
            _listState.value = res.fold(
                onSuccess = { lista -> RutaListState(data = lista) },
                onFailure = { e -> RutaListState(error = e.message ?: "Error desconocido") }
            )
        }
    }
}

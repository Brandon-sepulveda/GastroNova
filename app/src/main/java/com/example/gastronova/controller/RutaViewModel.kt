package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import com.example.gastronova.repository.FavoritosRepository
import com.example.gastronova.repository.RutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RutaListState(
    val loading: Boolean = false,
    val data: List<RutaDto> = emptyList(),
    val error: String? = null
)

data class FavoritosState(
    val loading: Boolean = false,
    val favoritosIds: Set<Int> = emptySet(),
    val error: String? = null
)

class RutaViewModel(
    private val repo: RutaRepository = RutaRepository(),
    private val favRepo: FavoritosRepository = FavoritosRepository()
) : ViewModel() {

    // ===============================
    //   ESTADOS QUE YA TENÍAS
    // ===============================
    private val _registerState = MutableStateFlow(SimpleUiState())
    val registerState: StateFlow<SimpleUiState> = _registerState

    private val _listState = MutableStateFlow(RutaListState())
    val listState: StateFlow<RutaListState> = _listState

    // ===============================
    //  FAVORITOS + MENSAJES
    // ===============================
    private val _favoritosState = MutableStateFlow(FavoritosState())
    val favoritosState: StateFlow<FavoritosState> = _favoritosState

    // ruta seleccionada en el selector (VerRutasUsuario)
    private val _selectedFavoritoRutaId = MutableStateFlow<Int?>(null)
    val selectedFavoritoRutaId: StateFlow<Int?> = _selectedFavoritoRutaId

    // Mensaje para mostrar en Snackbar/Toast
    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage

    // Para deshabilitar el botón mientras guarda
    private val _guardandoFavorito = MutableStateFlow(false)
    val guardandoFavorito: StateFlow<Boolean> = _guardandoFavorito

    fun consumirMensaje() {
        _uiMessage.value = null
    }

    fun seleccionarRutaFavorito(rutaId: Int?) {
        _selectedFavoritoRutaId.value = rutaId
    }

    // ===============================
    //   REGISTRO RUTA (YA TENÍAS)
    // ===============================
    fun registrar(nombre: String, descripcion: String?) {
        registrarConRestaurantes(
            nombre = nombre,
            descripcion = descripcion,
            restaurantIds = emptyList()
        )
    }

    fun registrarConRestaurantes(
        nombre: String,
        descripcion: String?,
        restaurantIds: List<Long>
    ) {
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

    // ===============================
    //   NUEVO: FAVORITOS
    // ===============================
    fun cargarFavoritos(usuarioId: Int) {
        _favoritosState.value = FavoritosState(loading = true)
        viewModelScope.launch {
            val res = favRepo.listarFavoritos(usuarioId)
            _favoritosState.value = res.fold(
                onSuccess = { lista ->
                    val ids = lista.mapNotNull { it.id }.toSet()
                    FavoritosState(favoritosIds = ids)
                },
                onFailure = { e ->
                    FavoritosState(error = e.message ?: "Error al cargar favoritos")
                }
            )
        }
    }

    fun guardarRutaSeleccionadaEnFavoritos(usuarioId: Int) {
        val rutaId = _selectedFavoritoRutaId.value
        if (rutaId == null) {
            _uiMessage.value = "Debes seleccionar una ruta"
            return
        }

        if (_favoritosState.value.favoritosIds.contains(rutaId)) {
            _uiMessage.value = "Esa ruta ya está en favoritos"
            return
        }

        _guardandoFavorito.value = true

        viewModelScope.launch {
            val res = favRepo.guardarFavorito(usuarioId, rutaId)
            res.fold(
                onSuccess = {
                    // ✅ Mensaje
                    _uiMessage.value = "Se guardó en favoritos"
                    // ✅ Limpiar selección del selector
                    _selectedFavoritoRutaId.value = null
                    // ✅ Marcarla como guardada para deshabilitarla
                    _favoritosState.value = _favoritosState.value.copy(
                        favoritosIds = _favoritosState.value.favoritosIds + rutaId
                    )
                },
                onFailure = { e ->
                    _uiMessage.value = e.message ?: "No se pudo guardar en favoritos"
                }
            )

            _guardandoFavorito.value = false
        }
    }
}

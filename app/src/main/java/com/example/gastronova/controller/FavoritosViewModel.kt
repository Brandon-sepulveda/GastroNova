package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.RutaDto
import com.example.gastronova.repository.FavoritosRepository
import com.example.gastronova.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estado para operaciones simples (guardar / eliminar)
data class FavoritosOperationState(
    val loading: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null
)

// Estado para la lista de rutas favoritas
data class FavoritosListState(
    val loading: Boolean = false,
    val data: List<RutaDto> = emptyList(),
    val error: String? = null
)

class FavoritosViewModel(
    private val repo: FavoritosRepository = FavoritosRepository()
) : ViewModel() {

    // usuarioId real, tomado desde el login (SessionManager)
    private val usuarioId: Int
        get() = SessionManager.usuarioId ?: 0

    private val _saveState = MutableStateFlow(FavoritosOperationState())
    val saveState: StateFlow<FavoritosOperationState> = _saveState

    private val _listState = MutableStateFlow(FavoritosListState())
    val listState: StateFlow<FavoritosListState> = _listState

    private val _deleteState = MutableStateFlow(FavoritosOperationState())
    val deleteState: StateFlow<FavoritosOperationState> = _deleteState

    // ==========================
    //   GUARDAR RUTA FAVORITA
    // ==========================
    fun guardar(rutaId: Int) {
        if (usuarioId == 0) {
            _saveState.value = FavoritosOperationState(
                success = false,
                error = "Usuario no logueado"
            )
            return
        }

        _saveState.value = FavoritosOperationState(loading = true)
        viewModelScope.launch {
            val res = repo.guardar(usuarioId, rutaId)
            _saveState.value = res.fold(
                onSuccess = { ok -> FavoritosOperationState(success = ok) },
                onFailure = { e ->
                    FavoritosOperationState(
                        success = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            )
        }
    }

    // ==========================
    //   LISTAR RUTAS FAVORITAS
    // ==========================
    fun cargarFavoritos() {
        if (usuarioId == 0) {
            _listState.value = FavoritosListState(
                data = emptyList(),
                error = "Usuario no logueado"
            )
            return
        }

        _listState.value = FavoritosListState(loading = true)
        viewModelScope.launch {
            val res = repo.listar(usuarioId)
            _listState.value = res.fold(
                onSuccess = { lista -> FavoritosListState(data = lista) },
                onFailure = { e ->
                    FavoritosListState(
                        data = emptyList(),
                        error = e.message ?: "Error desconocido"
                    )
                }
            )
        }
    }
    fun limpiarSaveState() {
        _saveState.value = FavoritosOperationState()
    }



    // ==========================
    //   ELIMINAR RUTA FAVORITA
    // ==========================
    fun eliminar(rutaId: Int) {
        if (usuarioId == 0) {
            _deleteState.value = FavoritosOperationState(
                success = false,
                error = "Usuario no logueado"
            )
            return
        }

        _deleteState.value = FavoritosOperationState(loading = true)
        viewModelScope.launch {
            val res = repo.eliminar(usuarioId, rutaId)
            _deleteState.value = res.fold(
                onSuccess = { ok -> FavoritosOperationState(success = ok) },
                onFailure = { e ->
                    FavoritosOperationState(
                        success = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            )
        }
    }
}

package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.UsuarioDto
import com.example.gastronova.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val loading: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null
)

class AuthViewModel(
    private val repo: UsuarioRepository = UsuarioRepository()
) : ViewModel() {

    // --- LOGIN ---
    private val _loginState = MutableStateFlow(AuthUiState())
    val loginState: StateFlow<AuthUiState> = _loginState

    fun login(usuario: String, contrasena: String) {
        _loginState.value = AuthUiState(loading = true)
        viewModelScope.launch {
            val res = repo.login(usuario, contrasena)
            _loginState.value = res.fold(
                onSuccess = { ok -> AuthUiState(success = ok) },
                onFailure = { e -> AuthUiState(error = e.message ?: "Error desconocido") }
            )
        }
    }

    // --- REGISTER ---
    private val _registerState = MutableStateFlow(AuthUiState())
    val registerState: StateFlow<AuthUiState> = _registerState

    fun registrar(
        nombre: String,
        apellido: String,
        correo: String,
        usuario: String,
        contrasena: String,
        tipo_usuario: Boolean = true
    ) {
        _registerState.value = AuthUiState(loading = true)
        viewModelScope.launch {
            val dto = UsuarioDto(
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                usuario = usuario,
                contrasena = contrasena,
                tipo_usuario = tipo_usuario
            )
            val res = repo.registrar(dto)
            _registerState.value = res.fold(
                onSuccess = { ok -> AuthUiState(success = ok) },
                onFailure = { e -> AuthUiState(error = e.message ?: "Error desconocido") }
            )
        }
    }
}

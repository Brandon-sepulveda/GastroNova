package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.LoginResponse
import com.example.gastronova.model.UsuarioDto
import com.example.gastronova.repository.UsuarioRepository
import com.example.gastronova.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val loading: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null,
    val usuario: LoginResponse? = null
)

class AuthViewModel(
    private val repo: UsuarioRepository = UsuarioRepository()
) : ViewModel() {

    private val _registerState = MutableStateFlow(AuthUiState())
    val registerState: StateFlow<AuthUiState> = _registerState

    private val _loginState = MutableStateFlow(AuthUiState())
    val loginState: StateFlow<AuthUiState> = _loginState

    // =====================
    //       REGISTRO
    // =====================
    fun registrar(
        nombre: String,
        apellido: String,
        correo: String,
        usuario: String,
        contrasena: String,
        tipoUsuario: Boolean = true
    ) {
        _registerState.value = AuthUiState(loading = true)
        viewModelScope.launch {
            val dto = UsuarioDto(
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                usuario = usuario,
                contrasena = contrasena,
                tipoUsuario = tipoUsuario
            )
            val res = repo.registrar(dto)
            _registerState.value = res.fold(
                onSuccess = { ok -> AuthUiState(success = ok) },
                onFailure = { e -> AuthUiState(error = e.message ?: "Error desconocido") }
            )
        }
    }

    // =====================
    //        LOGIN
    // =====================
    fun login(usuario: String, contrasena: String) {
        _loginState.value = AuthUiState(loading = true)
        viewModelScope.launch {
            val res = repo.login(usuario, contrasena)
            _loginState.value = res.fold(
                onSuccess = { resp ->
                    if (resp.success && resp.id != null) {
                        SessionManager.usuarioId = resp.id
                        SessionManager.usuarioNombre = resp.nombre
                        SessionManager.usuarioUsuario = resp.usuario
                        SessionManager.tipoUsuario = resp.tipoUsuario

                        AuthUiState(
                            success = true,
                            usuario = resp
                        )
                    } else {
                        AuthUiState(
                            success = false,
                            error = "Usuario o contraseña incorrectos"
                        )
                    }
                },
                onFailure = { e ->
                    AuthUiState(
                        success = false,
                        error = e.message ?: "Error de red o servidor"
                    )
                }
            )
        }
    }
}

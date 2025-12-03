package com.example.gastronova.repository

import com.example.gastronova.api.UsuarioApi
import com.example.gastronova.model.LoginRequest
import com.example.gastronova.model.LoginResponse
import com.example.gastronova.model.UsuarioDto
import com.example.gastronova.network.ApiClient

class UsuarioRepository(
    private val api: UsuarioApi = ApiClient.usuarioApi
) {
    suspend fun registrar(usuario: UsuarioDto): Result<Boolean> = runCatching {
        val r = api.registrar(usuario)
        if (r.isSuccessful) r.body() == true else error("HTTP ${r.code()}")
    }

    suspend fun login(usuario: String, contrasena: String): Result<LoginResponse> = runCatching {
        val r = api.login(LoginRequest(usuario, contrasena))
        if (r.isSuccessful) {
            r.body() ?: error("Respuesta vacía del servidor")
        } else {
            error("HTTP ${r.code()}")
        }
    }
}

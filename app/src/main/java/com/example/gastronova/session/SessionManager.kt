package com.example.gastronova.session

object SessionManager {
    var usuarioId: Int? = null
    var usuarioNombre: String? = null
    var usuarioUsuario: String? = null
    var tipoUsuario: Boolean? = null

    fun isLoggedIn(): Boolean = usuarioId != null

    fun isAdmin(): Boolean = tipoUsuario == true

    fun clear() {
        usuarioId = null
        usuarioNombre = null
        usuarioUsuario = null
        tipoUsuario = null
    }
}

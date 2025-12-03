package com.example.gastronova.api

import com.example.gastronova.model.LoginRequest
import com.example.gastronova.model.LoginResponse
import com.example.gastronova.model.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {

    @POST("usuario/register")
    suspend fun registrar(@Body usuario: UsuarioDto): Response<Boolean>

    @POST("usuario/login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>
}

package com.example.gastronova.api

import com.example.gastronova.model.RutaCreateRequest
import com.example.gastronova.model.RutaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RutaApi {

    @POST("ruta/register")
    suspend fun registrar(@Body request: RutaCreateRequest): Response<Unit>

    @GET("ruta/list")
    suspend fun listar(): Response<List<RutaDto>>
}

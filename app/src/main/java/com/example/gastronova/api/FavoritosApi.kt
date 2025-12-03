package com.example.gastronova.api

import com.example.gastronova.model.FavoritoRequest
import com.example.gastronova.model.RutaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritosApi {

    // ✅ No intentamos parsear Boolean, solo usamos el HTTP code
    @POST("favoritos/guardar")
    suspend fun guardar(@Body body: FavoritoRequest): Response<Unit>

    @GET("favoritos/list/{usuarioId}")
    suspend fun listar(@Path("usuarioId") usuarioId: Int): Response<List<RutaDto>>

    // Retrofit no soporta bodies en @DELETE directamente, se usa @HTTP
    @HTTP(method = "DELETE", path = "favoritos/eliminar", hasBody = true)
    suspend fun eliminar(@Body body: FavoritoRequest): Response<Unit>
}

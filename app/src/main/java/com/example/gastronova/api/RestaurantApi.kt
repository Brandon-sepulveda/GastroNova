package com.example.gastronova.api

import com.example.gastronova.model.RestaurantDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestaurantApi {

    @POST("restaurant/register")
    suspend fun registrar(@Body restaurant: RestaurantDto): Response<Boolean>

    @GET("restaurant/list")
    suspend fun listar(): Response<List<RestaurantDto>>
}

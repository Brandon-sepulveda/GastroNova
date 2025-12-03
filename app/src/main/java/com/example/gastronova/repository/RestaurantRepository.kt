package com.example.gastronova.repository

import com.example.gastronova.api.RestaurantApi
import com.example.gastronova.model.RestaurantDto
import com.example.gastronova.network.ApiClient

class RestaurantRepository(
    private val api: RestaurantApi = ApiClient.restaurantApi
) {

    suspend fun registrar(restaurant: RestaurantDto): Result<Boolean> {
        return try {
            val response = api.registrar(restaurant)

            if (response.isSuccessful) {
                // body puede ser true / false / null
                val body = response.body()
                Result.success(body == true)
            } else if (response.code() == 409) {
                // 409 = nombre duplicado
                Result.success(false)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()} al registrar restaurante"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listar(): Result<List<RestaurantDto>> {
        return try {
            val response = api.listar()

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure(Exception("Error HTTP ${response.code()} al listar restaurantes"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

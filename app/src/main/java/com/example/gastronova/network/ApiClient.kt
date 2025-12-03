package com.example.gastronova.network

import com.example.gastronova.api.FavoritosApi
import com.example.gastronova.api.RestaurantApi
import com.example.gastronova.api.RutaApi
import com.example.gastronova.api.UsuarioApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "http://192.168.18.242:8080/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()


    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // --- Usuario ---
    val usuarioApi: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java)
    }

    // --- Restaurant ---
    val restaurantApi: RestaurantApi by lazy {
        retrofit.create(RestaurantApi::class.java)
    }

    // --- Ruta ---
    val rutaApi: RutaApi by lazy {
        retrofit.create(RutaApi::class.java)
    }

    // --- Favoritos (rutas guardadas) ---
    val favoritosApi: FavoritosApi by lazy {
        retrofit.create(FavoritosApi::class.java)
    }
}

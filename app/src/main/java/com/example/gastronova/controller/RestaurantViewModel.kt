package com.example.gastronova.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gastronova.model.RestaurantDto
import com.example.gastronova.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SimpleUiState(
    val loading: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null
)

data class RestaurantListState(
    val loading: Boolean = false,
    val data: List<RestaurantDto> = emptyList(),
    val error: String? = null
)

class RestaurantViewModel(
    private val repo: RestaurantRepository = RestaurantRepository()
) : ViewModel() {

    private val _registerState = MutableStateFlow(SimpleUiState())
    val registerState: StateFlow<SimpleUiState> = _registerState

    private val _listState = MutableStateFlow(RestaurantListState())
    val listState: StateFlow<RestaurantListState> = _listState


    fun registrar(nombre: String, descripcion: String?) {
        _registerState.value = SimpleUiState(loading = true)
        viewModelScope.launch {
            val dto = RestaurantDto(
                nombre = nombre,
                direccionText = null,
                descripcion = descripcion
            )
            val res = repo.registrar(dto)
            _registerState.value = res.fold(
                onSuccess = { ok -> SimpleUiState(success = ok) },
                onFailure = { e -> SimpleUiState(error = e.message ?: "Error desconocido") }
            )
        }
    }


    fun registrarRestaurant(
        nombre: String,
        empresa: String,
        ubicacion: String,
        tipo: String,
        descripcion: String
    ) {
        registrar(nombre, descripcion)
    }

    fun cargarRestaurants() {
        _listState.value = RestaurantListState(loading = true)
        viewModelScope.launch {
            val res = repo.listar()
            _listState.value = res.fold(
                onSuccess = { lista -> RestaurantListState(data = lista) },
                onFailure = { e -> RestaurantListState(error = e.message ?: "Error desconocido") }
            )
        }
    }
}

package com.example.project_cs426.viewmodel

import com.example.project_cs426.data.remote.model.CountryDto

sealed class LocationUiState {
    object Loading : LocationUiState()
    data class Success(val countries: List<CountryDto>) : LocationUiState()
    data class Error(val message: String) : LocationUiState()
}

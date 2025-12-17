package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.remote.RetrofitClient
import com.example.project_cs426.data.remote.api.CountriesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    private val api: CountriesApi = RetrofitClient.api
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LocationUiState>(LocationUiState.Loading)
    val uiState: StateFlow<LocationUiState> = _uiState
    private val allowedCountries = listOf(
        "Egypt",
        "Saudi Arabia",
        "United Arab Emirates",
        "Kuwait",
        "Qatar",
        "Jordan",
        "Oman",
        "Bahrain",
        "Iraq",
        "Morocco"
    )

    fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = LocationUiState.Loading
            try {
                val response = RetrofitClient.api.getCountries()

                val filtered = response.data.filter {
                    it.country in allowedCountries
                }

                _uiState.value = LocationUiState.Success(filtered)

            } catch (e: Exception) {
                _uiState.value = LocationUiState.Error(
                    message = "No internet connection. Please try again."
                )



            }
        }
    }
}

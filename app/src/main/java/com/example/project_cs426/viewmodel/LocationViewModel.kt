package com.example.project_cs426.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.remote.RetrofitClient
import com.example.project_cs426.data.remote.model.CountryDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _countries = MutableStateFlow<List<CountryDto>>(emptyList())
    val countries: StateFlow<List<CountryDto>> = _countries

    val allowedCountries = listOf(
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
            try {
                val response = RetrofitClient.api.getCountries()
                _countries.value = response.data.filter {
                    it.country in allowedCountries
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

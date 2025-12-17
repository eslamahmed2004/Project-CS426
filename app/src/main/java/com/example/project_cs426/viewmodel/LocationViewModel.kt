package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.remote.RetrofitClient
import com.example.project_cs426.data.remote.api.CountriesApi
import com.example.project_cs426.data.remote.model.CountriesResponse
import com.example.project_cs426.data.remote.model.CountryDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocationViewModel(
    private val api: CountriesApi = RetrofitClient.api
) : ViewModel() {

    private val _countries = MutableStateFlow<List<CountryDto>>(emptyList())
    val countries: StateFlow<List<CountryDto>> = _countries

    // (اختياري) قائمة مسموح بها لتصفية النتائج المحلية — عدّل أو احذف حسب حاجتك
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
            try {
                val response = withContext(Dispatchers.IO) {
                    api.getCountries()
                }

                // API عندك ترجع CountriesResponse(data = List<CountryDto>)
                if (response is CountriesResponse) {
                    // لو عايز تصفي حسب allowedCountries:
                    _countries.value = response.data.filter { it.country in allowedCountries }
                    // أو لو لا تصفية:
                    // _countries.value = response.data
                } else {
                    // مرونة لو API قد ترجع قائمة مباشرة (نادراً)
                    @Suppress("UNCHECKED_CAST")
                    if (response is List<*>) {
                        _countries.value = (response as? List<CountryDto>) ?: emptyList()
                    } else {
                        _countries.value = emptyList()
                    }
                }
            } catch (t: Exception) {
                t.printStackTrace()
                _countries.value = emptyList()
            }
        }
    }
}

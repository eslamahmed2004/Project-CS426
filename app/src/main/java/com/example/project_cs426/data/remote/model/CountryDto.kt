package com.example.project_cs426.data.remote.model

data class CountryDto(
    val iso2: String,
    val iso3: String,
    val country: String,
    val cities: List<String>
)

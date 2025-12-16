package com.example.project_cs426.data.remote.model

data class CountriesResponse(
    val error: Boolean,
    val msg: String,
    val data: List<CountryDto>
)

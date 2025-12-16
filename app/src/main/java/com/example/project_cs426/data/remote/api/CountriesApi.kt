package com.example.project_cs426.data.remote.api

import com.example.project_cs426.data.remote.model.CountriesResponse
import retrofit2.http.GET

interface CountriesApi {

    @GET("api/v0.1/countries")
    suspend fun getCountries(): CountriesResponse
}

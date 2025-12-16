package com.example.project_cs426.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "location_prefs")

class LocationPreferences(private val context: Context) {

    companion object {
        private val COUNTRY_KEY = stringPreferencesKey("country")
        private val CITY_KEY = stringPreferencesKey("city")
    }

    suspend fun saveLocation(country: String, city: String) {
        context.dataStore.edit { prefs ->
            prefs[COUNTRY_KEY] = country
            prefs[CITY_KEY] = city
        }
    }

    val country: Flow<String> =
        context.dataStore.data.map { prefs ->
            prefs[COUNTRY_KEY] ?: ""
        }

    val city: Flow<String> =
        context.dataStore.data.map { prefs ->
            prefs[CITY_KEY] ?: ""
        }
}

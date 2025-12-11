package com.example.project_cs426.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
//
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val passwordVisible = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val usernameError = mutableStateOf<String?>(null)

    fun validateSignup(): Boolean {
        var valid = true

        if (username.value.isBlank()) {
            usernameError.value = "Username is required"
            valid = false
        } else {
            usernameError.value = null
        }

        return valid
    }

    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    fun clearFields() {
        username.value = ""
        email.value = ""
        password.value = ""
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.value.isBlank() || password.value.isBlank()) {
            onError("Email or Password cannot be empty")
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            delay(1200)
            isLoading.value = false
            onSuccess()
        }
    }

    fun signup(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (username.value.isBlank() || email.value.isBlank() || password.value.isBlank()) {
            onError("All fields are required")
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            delay(1200)
            isLoading.value = false
            onSuccess()
        }
    }
}

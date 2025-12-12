package com.example.project_cs426.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.project_cs426.model.User

class UserViewModel : ViewModel() {

    var currentUser = mutableStateOf<User?>(null)
        private set

    fun loginUser(name: String, email: String, image: Int?) {
        currentUser.value = User(
            name = name,
            email = email,
            image = image ?: 0
        )
    }

    fun logout() {
        currentUser.value = null
    }
}

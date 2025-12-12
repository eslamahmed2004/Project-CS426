package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.project_cs426.model.User
import com.example.project_cs426.model.FakeData

class AccountViewModel : ViewModel() {

    var user by mutableStateOf(FakeData.user)
        private set

    fun logout() {
        // TODO: Add logout logic
    }
}

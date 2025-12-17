package com.example.project_cs426.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.local.dao.UserDao
import com.example.project_cs426.data.local.entity.UserEntity
import com.example.project_cs426.model.User
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userDao: UserDao
) : ViewModel() {

    // ───────── UI STATE ─────────
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val passwordVisible = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)


    val currentUser = mutableStateOf<User?>(null)
    // ───────── Helpers ─────────
    fun togglePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    fun clearFields() {
        username.value = ""
        email.value = ""
        password.value = ""
        errorMessage.value = null
    }

    // ───────── LOGIN ─────────
    fun login(
        onSuccessUser: () -> Unit,
        onSuccessAdmin: () -> Unit
    ) {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Email and password are required"
            return
        }

        viewModelScope.launch {
            isLoading.value = true

            val user = userDao.login(
                email = email.value.trim(),
                password = password.value
            )

            isLoading.value = false

            if (user == null) {
                errorMessage.value = "Invalid email or password"
            } else {
                errorMessage.value = null

                currentUser.value = User(
                    name = user.name,
                    email = user.email,
                    image = null
                )


                if (user.role == "ADMIN") {
                    onSuccessAdmin()
                } else {
                    onSuccessUser()
                }
            }
        }
    }

    // ───────── REGISTER ─────────
    fun signup(
        onSuccess: () -> Unit
    ) {
        if (
            username.value.isBlank() ||
            email.value.isBlank() ||
            password.value.isBlank()
        ) {
            errorMessage.value = "All fields are required"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            val exists = userDao.isEmailExists(email.value.trim())
            if (exists > 0) {
                isLoading.value = false
                errorMessage.value = "Email already exists"
                return@launch
            }

            userDao.insertUser(
                UserEntity(
                    name = username.value.trim(),
                    email = email.value.trim(),
                    password = password.value,
                    role = "USER"
                )
            )

            isLoading.value = false
            clearFields()
            onSuccess()
        }
    }

    // ───────── SEED ADMIN ─────────
    fun seedAdminIfNotExists() {
        viewModelScope.launch {
            val exists = userDao.isEmailExists("admin@gmail.com")
            if (exists == 0) {
                userDao.insertUser(
                    UserEntity(
                        name = "Admin",
                        email = "admin@gmail.com",
                        password = "1234",
                        role = "ADMIN"
                    )
                )
            }
        }
    }

    fun logout() {
        currentUser.value = null
        email.value = ""
        password.value = ""
    }
}

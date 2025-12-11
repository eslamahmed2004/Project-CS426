package com.example.project_cs426

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import com.example.project_cs426.pages.auth.StartPage
import com.example.project_cs426.pages.auth.location
import com.example.project_cs426.pages.auth.login
import com.example.project_cs426.pages.auth.onbording
import com.example.project_cs426.pages.auth.signup

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.startPage
    ) {
        composable(Routes.startPage) {
            StartPage(navController)
        }

        composable(Routes.onbording) {
            onbording(navController)
        }
        composable(Routes.location) {
            location(navController)
        }

        composable(Routes.login) {
            login(navController)
        }
        composable(Routes.signup) {
            signup(navController)
        }
        }
}



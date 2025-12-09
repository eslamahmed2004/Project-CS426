package com.example.project_cs426

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import com.example.project_cs426.pages.location.location
import com.example.project_cs426.pages.onbording.onbording
import com.example.project_cs426.pages.start.StartPage

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
        }
}



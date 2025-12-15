package com.example.project_cs426

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.navigation.AppNavGraph
import com.example.project_cs426.navigation.BottomBar
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.ui.theme.ProjectCS426Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        setContent {
            ProjectCS426Theme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    val navController = rememberNavController()
                    val navBackStackEntry = navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry.value?.destination?.route

                    // الصفحات اللي مش عايزين فيها BottomBar
                    val pagesWithoutBottomBar = listOf(
                        Routes.START,
                        Routes.ONBOARDING,
                        Routes.LOCATION,
                        Routes.LOGIN,
                        Routes.REGISTER,
                        Routes.ADMIN_DASHBOARD
                    )

                    Scaffold(
                        containerColor = Color.White,
                        bottomBar = {
                            if (currentRoute !in pagesWithoutBottomBar) {
                                BottomBar(navController)
                            }
                        }
                    ) { padding ->
                        Box(Modifier
                            .fillMaxSize().padding(padding)
                        ) {
                            AppNavGraph(navController)
                        }
                    }
                }
            }
        }
    }
}

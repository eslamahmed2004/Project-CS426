package com.example.project_cs426.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.project_cs426.R
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.PrimaryGreen

@Composable
fun BottomBar(navController: NavController) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = Color.White
    ){

        NavigationBarItem(
            selected = currentRoute == Routes.home,
            onClick = { navController.navigate(Routes.home) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                unselectedIconColor = Black,
                selectedTextColor = PrimaryGreen,
                unselectedTextColor = Black
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Explorer,
            onClick = { navController.navigate(Routes.Explorer) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_explore),
                    contentDescription = "Explore"
                )
            },
            label = { Text("Explore") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                unselectedIconColor = Black,
                selectedTextColor = PrimaryGreen,
                unselectedTextColor = Black
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.cart,
            onClick = { navController.navigate(Routes.cart) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_cart),
                    contentDescription = "Cart"
                )
            },
            label = { Text("Cart") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                unselectedIconColor = Black,
                selectedTextColor = PrimaryGreen,
                unselectedTextColor = Black
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.favourite,
            onClick = { navController.navigate(Routes.favourite) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_favorite),
                    contentDescription = "Favorite"
                )
            },
            label = { Text("Favorite") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                unselectedIconColor = Black,
                selectedTextColor = PrimaryGreen,
                unselectedTextColor = Black
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Account,
            onClick = { navController.navigate(Routes.Account) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_account),
                    contentDescription = "Account"
                )
            },
            label = { Text("Account") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryGreen,
                unselectedIconColor = Black,
                selectedTextColor = PrimaryGreen,
                unselectedTextColor = Black
            )
        )
    }
}

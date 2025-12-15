package com.example.project_cs426.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project_cs426.pages.auth.*
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Error
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.pages.favourite.Favourite
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.pages.home.HomeScreen
import com.example.project_cs426.pages.product.Explore
import com.example.project_cs426.pages.product.ProductsByCategoryScreen
import com.example.project_cs426.pages.product.ProductsBySearchScreen
import com.example.project_cs426.pages.product.ProductDetailsScreen
import com.example.project_cs426.viewmodel.ProductViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    val cartVm: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.startPage) {
        composable(Routes.startPage) { StartPage(navController) }
        composable(Routes.onbording) { onbording(navController) }
        composable(Routes.location) { location(navController) }
        composable(Routes.login) { login(navController) }
        composable(Routes.signup) { register(navController) }

        composable(Routes.home) { HomeScreen(navController = navController) }
        composable(Routes.Explorer) { Explore(navController) }

        composable(Routes.PRODUCT_CATEGORY, arguments = listOf(navArgument("categoryName") { type = NavType.StringType })) {
            val category = it.arguments?.getString("categoryName") ?: ""
            ProductsByCategoryScreen(category, navController)
        }

        composable(Routes.PRODUCT_SEARCH, arguments = listOf(navArgument("query") { type = NavType.StringType })) {
            val query = it.arguments?.getString("query") ?: ""
            ProductsBySearchScreen(query, navController)
        }

        composable(Routes.PRODUCT_DETAILS, arguments = listOf(navArgument("productId") { type = NavType.IntType })) { backStack ->
            val productVm: ProductViewModel = viewModel()
            val id = backStack.arguments?.getInt("productId") ?: 0
            productVm.loadProduct(id)
            ProductDetailsScreen(navController, productVm)
        }

        composable(Routes.cart) {
            Cart(cartViewModel = cartVm, onNavigateTo = { route -> navController.navigate(route) })
        }

        composable(Routes.favourite) {
            Favourite(cartViewModel = cartVm, onNavigateTo = { route -> navController.navigate(route) })
        }

        composable(Routes.checkout) {
            Checkout(navController = navController, totalPrice = cartVm.getTotalPrice())
        }

        composable(Routes.success) { Success(navController = navController) }
        composable(Routes.error) { Error(navController = navController) }

        // account, other routes...
    }
}

package com.example.project_cs426.navigation

import androidx.compose.runtime.Composable
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project_cs426.pages.home.HomeScreen
import com.example.project_cs426.pages.product.Explore
import com.example.project_cs426.pages.product.ProductsByCategoryScreen
import com.example.project_cs426.pages.product.ProductsBySearchScreen
import com.example.ecommerce.pages.account.AccountScreen
import com.example.project_cs426.R
import com.example.project_cs426.model.User
import com.example.project_cs426.pages.auth.*
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.pages.checkout.*
import com.example.project_cs426.pages.favourite.Favourite
import com.example.project_cs426.pages.product.ProductDetailsScreen
import com.example.project_cs426.viewmodel.*

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.startPage
    ) {

        composable(Routes.startPage) { StartPage(navController) }
        composable(Routes.onbording) { onbording(navController) }
        composable(Routes.location) { location(navController) }
        composable(Routes.login) { login(navController) }
        composable(Routes.Register) { register(navController) }


        composable(Routes.home {
            HomeScreen(navController = navController)
        }

        composable(Routes.Explorer) {
            Explore(navController = navController)
        }

        composable(
            route = Routes.PRODUCT_CATEGORY,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) {
            val category = it.arguments?.getString("categoryName") ?: ""
            ProductsByCategoryScreen(category, navController)
        }

        composable(
            route = Routes.PRODUCT_SEARCH,
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) {
            val query = it.arguments?.getString("query") ?: ""
            ProductsBySearchScreen(query, navController)
        }

        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productVm: ProductViewModel = viewModel()
            val id = backStackEntry.arguments?.getInt("productId") ?: 0

            LaunchedEffect(id) {
                productVm.loadProduct(id)
            }

            ProductDetailsScreen(navController, productVm)
        }


        composable(Routes.cart) {
            val cartVm: CartViewModel = viewModel()
            Cart(cartVm) { navController.navigate(it) }
        }

        composable(Routes.favourite) {
            val cartVm: CartViewModel = viewModel()
            Favourite(cartVm) { navController.navigate(it) }
        }


        composable(Routes.checkout) {
            val cartVm: CartViewModel = viewModel()
            Checkout(navController, cartVm.getTotalPrice())
        }

        composable(Routes.success) { Success(navController) }
        composable(Routes.error) { Error(navController) }


        composable(Routes.Account) {
            val authVm: AuthViewModel = viewModel()

            AccountScreen(
                user = User(
                    name = authVm.username.value,
                    email = authVm.email.value,
                    image = R.drawable.screenshot
                ),
                onOrdersClick = {},
                onMyDetailsClick = {},
                onAddressClick = {},
                onPaymentMethodsClick = {},
                onPromoCodeClick = {},
                onAboutClick = {},
                onLogoutClick = {}
            )
        }
    }
}

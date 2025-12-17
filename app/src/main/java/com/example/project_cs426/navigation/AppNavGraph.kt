package com.example.project_cs426.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommerce.pages.account.AccountScreen
import com.example.project_cs426.R
import com.example.project_cs426.model.User
import com.example.project_cs426.pages.admin.AdminDashboardRoute
import com.example.project_cs426.pages.auth.StartPage
import com.example.project_cs426.pages.auth.location
import com.example.project_cs426.pages.auth.login
import com.example.project_cs426.pages.auth.onbording
import com.example.project_cs426.pages.auth.register
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Error
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.pages.favourite.Favourite
import com.example.project_cs426.pages.home.HomeScreen
import com.example.project_cs426.pages.product.Explore
import com.example.project_cs426.pages.product.ProductDetailsScreen
import com.example.project_cs426.pages.product.ProductsByCategoryScreen
import com.example.project_cs426.pages.product.ProductsBySearchScreen
import com.example.project_cs426.viewmodel.AuthViewModel
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.viewmodel.ProductViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.START
    ) {

        composable(Routes.START) { StartPage(navController) }
        composable(Routes.ONBOARDING) { onbording(navController) }
        composable(Routes.LOCATION) { location(navController) }
        composable(Routes.LOGIN) { login(navController) }
        composable(Routes.REGISTER) { register(navController) }




        composable(Routes.ADMIN_DASHBOARD) {
            AdminDashboardRoute(navController)
        }


        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.EXPLORE) {
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


        composable(Routes.CART) {
            Cart { navController.navigate(it) }
        }


        composable(
            route = Routes.PRODUCT_DETAILS,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0

            val viewModel: ProductViewModel = viewModel()
            viewModel.loadProduct(productId)

            ProductDetailsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }


//        composable(Routes.FAVORITE) {
//            Favourite { navController.navigate(it) }
//        }
//


//        composable(Routes.CHECKOUT) {
//            val cartVm: CartViewModel = viewModel()
//            Checkout(navController, cartVm.getTotalPrice())
//        }
//
//        composable(Routes.SUCCESS) { Success(navController) }
//        composable(Routes.ERROR) { Error(navController) }


        composable(Routes.ACCOUNT) {
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
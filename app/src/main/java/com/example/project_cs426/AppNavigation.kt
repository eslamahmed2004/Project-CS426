package com.example.project_cs426

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommerce.screens.account.AccountScreen
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import com.example.project_cs426.model.User
import com.example.project_cs426.pages.auth.StartPage
import com.example.project_cs426.pages.auth.location
import com.example.project_cs426.pages.auth.login
import com.example.project_cs426.pages.auth.onbording
import com.example.project_cs426.pages.auth.signup
import com.example.project_cs426.screens.product.ProductDetailsScreen
import com.example.project_cs426.viewmodel.AuthViewModel
import com.example.project_cs426.viewmodel.ProductViewModel
import com.example.project_cs426.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.pages.favourite.Favourite
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.pages.checkout.Error


@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = Routes.startPage
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
        // Cart
        composable(Routes.cart) {
            val cartVm: CartViewModel = viewModel()
            Cart(
                cartViewModel = cartVm, onNavigateTo = { route -> navController.navigate(route) })
        }

// Favourite
        composable(Routes.favourite) {
            val cartVm: CartViewModel = viewModel()
            Favourite(
                cartViewModel = cartVm, onNavigateTo = { route -> navController.navigate(route) })
        }

// Checkout (modal-like page)
        composable(Routes.checkout) {
            val cartVm: CartViewModel = viewModel()
            Checkout(navController = navController, totalPrice = cartVm.getTotalPrice())
        }

// PlaceOrder (success)
        composable(Routes.success) {
            Success(navController = navController)
        }

// Error screen
        composable(Routes.error) {
            Error(navController = navController)
        }



        composable(Routes.Account) {
            // لو عندك ViewModel للّوجين
            val authViewModel: AuthViewModel = viewModel()

            AccountScreen(
                user = User(
                name = authViewModel.username.value,
                email = authViewModel.email.value,
                image = R.drawable.screenshot
            ),
                onOrdersClick = { navController.navigate("orders") },
                onMyDetailsClick = { navController.navigate("myDetails") },
                onAddressClick = { navController.navigate("address") },
                onPaymentMethodsClick = { navController.navigate("paymentMethods") },
                onPromoCodeClick = { navController.navigate("promoCode") },
                onAboutClick = { navController.navigate("about") },
                onLogoutClick = { /* مفيش حاجة */ })
        }





        composable(Routes.filters) {
            FiltersScreen(onClose = { navController.popBackStack() }, onApply = { category, brand ->
                // لو عايز تستخدم قيم الفيلتر
                // println(category)
                // println(brand)

                navController.popBackStack()
            })
        }


        composable(
            route = "productDetails/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: ProductViewModel = viewModel()
            val id = backStackEntry.arguments?.getInt("productId") ?: 0

            LaunchedEffect(id) { viewModel.loadProduct(id) }

            ProductDetailsScreen(
                navController = navController, viewModel = viewModel
            )
        }

//
//        composable("orders") { OrdersScreen() }
//        composable("myDetails") { MyDetailsScreen() }
//        composable("address") { AddressScreen() }
//        composable("paymentMethods") { PaymentScreen() }
//        composable("promoCode") { PromoScreen() }
//        composable("about") { AboutScreen() }


    }
}



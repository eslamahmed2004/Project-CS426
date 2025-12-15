package com.example.project_cs426

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommerce.pages.account.AccountScreen
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import com.example.project_cs426.model.User
import com.example.project_cs426.pages.auth.StartPage
import com.example.project_cs426.pages.auth.location
import com.example.project_cs426.pages.auth.login
import com.example.project_cs426.pages.auth.onbording
import com.example.project_cs426.pages.auth.register
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Error
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.pages.product.ProductDetailsScreen
import com.example.project_cs426.viewmodel.AuthViewModel
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.viewmodel.ProductViewModel
import com.example.project_cs426.viewmodel.UserViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
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
            register(navController)
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
        composable(Routes.cart) {
            Cart(
                cartViewModel = cartViewModel, onNavigateTo = { route ->
                    navController.navigate(route)
                })
        }

        /* ================= Checkout (Bottom Sheet UI) ================= */
        composable(Routes.checkout) {
            Checkout(
                navController = navController, totalPrice = cartViewModel.getTotalPrice()
            )
        }

        /* ================= Success / Place Order ================= */
        composable(Routes.success) {
            Success(navController = navController)
        }

        /* ================= Error ================= */
        composable(Routes.error) {
            Error(
                navController = navController, onRetry = {
                    navController.popBackStack()
                })
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



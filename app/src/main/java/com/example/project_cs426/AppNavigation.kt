package com.example.project_cs426

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce.pages.account.AccountScreen
import com.example.project_cs426.model.User
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.pages.auth.StartPage
import com.example.project_cs426.pages.auth.location
import com.example.project_cs426.pages.auth.login
import com.example.project_cs426.pages.auth.onbording
import com.example.project_cs426.pages.auth.register
import com.example.project_cs426.pages.cart.Cart
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Error
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.viewmodel.AuthViewModel
import com.example.project_cs426.viewmodel.CartViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
    NavHost(
        navController = navController, startDestination = Routes.START
    ) {
        composable(Routes.START) {
            StartPage(navController)
        }

        composable(Routes.ONBOARDING) {
            onbording(navController)
        }
        composable(Routes.LOCATION) {
            location(navController)
        }

        composable(Routes.LOGIN) {
            login(navController)
        }
        composable(Routes.REGISTER) {
            register(navController)
        }



        composable(Routes.ACCOUNT) {
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
        composable(Routes.CART) {
            Cart(
                cartViewModel = cartViewModel, onNavigateTo = { route ->
                    navController.navigate(route)
                })
        }

        /* ================= Checkout (Bottom Sheet UI) ================= */
        composable(Routes.CHECKOUT) {
            Checkout(
                navController = navController, totalPrice = cartViewModel.getTotalPrice()
            )
        }

        /* ================= Success / Place Order ================= */
        composable(Routes.SUCCESS) {
            Success(navController = navController)
        }

        /* ================= Error ================= */
        composable(Routes.ERROR) {
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



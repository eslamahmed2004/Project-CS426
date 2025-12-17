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
import com.example.project_cs426.pages.cart.CartViewModel
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.pages.checkout.Error
import com.example.project_cs426.pages.checkout.Success
import com.example.project_cs426.pages.favourite.Favourite
import com.example.project_cs426.pages.favourite.FavouriteViewModel
import com.example.project_cs426.pages.home.HomeScreen
import com.example.project_cs426.pages.product.Explore
import com.example.project_cs426.pages.product.ProductDetailsScreen
import com.example.project_cs426.pages.product.ProductsByCategoryScreen
import com.example.project_cs426.pages.product.ProductsBySearchScreen
import com.example.project_cs426.viewmodel.AuthViewModel

import com.example.project_cs426.viewmodel.ProductViewModel

@Composable
fun AppNavGraph(navController: NavHostController)  {
    val cartViewModel: CartViewModel = viewModel()
    val favouriteViewModel: FavouriteViewModel = viewModel()
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

        // داخل AppNavigation composable، في composable(Routes.favourite) {...}
        composable(Routes.favourite) {
            Favourite(
                navController = navController,
                favouritesViewModel = favouriteViewModel,
                onAddAllToCart = { favouriteItems ->
                    // تحويل المفضلات إلى عناصر سلة (Cart)
                    val cartItems = favouriteViewModel.toCartItems(favouriteItems)

                    // إضافة العناصر إلى السلة عبر CartViewModel
                    cartViewModel.addAllToCart(cartItems)

                    // الانتقال إلى شاشة السلة
                    navController.navigate(Routes.cart)
                }
            )
        }



        composable(Routes.cart) {
            Cart(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(Routes.checkout) {
            Checkout(
                total = cartViewModel.totalPrice(),            // total param name matches Checkout.kt
                onDismiss = { navController.popBackStack() }, // close sheet / go back
                onPlaceOrder = {
                    // after placing order -> go to success screen
                    navController.navigate(Routes.success) {
                        // optional: adjust back stack if you want
                        popUpTo(Routes.home) { inclusive = false }
                    }
                }
            )
        }

        /* ==== Success / Error ==== */
        composable(Routes.success) {
            Success(navController = navController)
        }

        composable(Routes.error) {
            Error(
                onDismiss = { navController.popBackStack() },
                onRetry = {
                    navController.popBackStack()
                    navController.navigate(Routes.checkout)
                },
                onBackHome = {
                    navController.navigate(Routes.home) {
                        popUpTo(Routes.home) { inclusive = true }
                    }
                }
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

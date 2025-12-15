package com.example.project_cs426.navigation

object Routes {

    // Auth
    const val START = "start"
    const val ONBOARDING = "onboarding"
    const val LOCATION = "location"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PRIVACY = "privacy"
    const val TERMS = "terms"


    // Main
    const val HOME = "home"
    const val EXPLORE = "explore"
    const val FAVORITE = "favorite"
    const val CART = "cart"
    const val CHECKOUT = "checkout"
    const val SUCCESS = "success"
    const val ERROR = "error"
    const val ACCOUNT = "account"

    const val FILTERS = "filters"

    // Product
    const val PRODUCT_DETAILS = "productDetails/{productId}"
    const val PRODUCT_CATEGORY = "productsByCategory/{categoryName}"
    const val PRODUCT_SEARCH = "productsBySearch/{query}"

    ///
    const val  ADMIN_DASHBOARD ="admin_dashboard"
}

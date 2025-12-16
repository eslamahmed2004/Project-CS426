package com.example.project_cs426.model

import com.example.project_cs426.R
import com.example.project_cs426.pages.favourite.FavouriteItem
import com.example.project_cs426.pages.favourite.FavouriteItemUi

object FakeData {

    val products = listOf(
        Product(
            id = 1,
            name = "Organic Bananas",
            image = R.drawable.apple,
            price = 4.99,
            description = "Fresh organic bananas rich in nutrients.",
            weight = "1kg",
            category = "Fruits",
            brand = "Organic Farm"
        ),
        Product(
            id = 2,
            name = "Red Apple",
            image = R.drawable.apple,
            price = 3.49,
            description = "Crispy red apples full of vitamins.",
            weight = "1kg",
            category = "Fruits",
            brand = "Nature Fresh"
        ),
        Product(
            id = 3,
            name = "Orange",
            image = R.drawable.apple,
            price = 2.99,
            description = "Juicy oranges rich in vitamin C.",
            weight = "1kg",
            category = "Fruits",
            brand = "Citrus Valley"
        ),
        Product(
            id = 4,
            name = "Tomato",
            image = R.drawable.apple,
            price = 1.99,
            description = "Fresh tomatoes perfect for cooking.",
            weight = "1kg",
            category = "Vegetables",
            brand = "Green Farm"
        ),
        Product(
            id = 5,
            name = "Potato",
            image = R.drawable.apple,
            price = 0.99,
            description = "High quality potatoes for daily use.",
            weight = "2kg",
            category = "Vegetables",
            brand = "Farm House"
        ),
        Product(
            id = 6,
            name = "Fresh Milk",
            image = R.drawable.apple,
            price = 5.99,
            description = "Pure fresh milk rich in calcium.",
            weight = "2L",
            category = "Dairy",
            brand = "Daily Milk"
        ),
        Product(
            id = 7,
            name = "Cheese",
            image = R.drawable.apple,
            price = 9.99,
            description = "Delicious creamy cheese.",
            weight = "500g",
            category = "Dairy",
            brand = "Cheesy Delight"
        )
    )
    val sampleFavourites = listOf(
        FavouriteItemUi(
            id = "1",
            name = "Sprite Can",
            subtitle = "325ml, Price",
            price = 1.50,
            imageRes = R.drawable.sprite_can
        ),
        FavouriteItemUi(id = "2", name = "Diet Coke", subtitle = "355ml, Price", price = 1.99, imageRes = R.drawable.diet_coke),
        FavouriteItemUi(id = "3", name = "Apple & Grape Juice", subtitle = "2L, Price", price = 15.50, imageRes = R.drawable.apple_grape_juice),
        FavouriteItemUi(id = "4", name = "Coca Cola Can", subtitle = "325ml, Price", price = 4.99, imageRes = R.drawable.coca_cola_can),
        FavouriteItemUi(id = "5", name = "Pepsi Can", subtitle = "330ml, Price", price = 4.99, imageRes = R.drawable.pepsi_can)
    )

    val categories = listOf(
        CategoryProducts(
            category = "Fruits",
            image = R.drawable.fruits_category,
            color = 0xFF53B175,
            products = products.filter { it.category == "Fruits" }
        ),
        CategoryProducts(
            category = "Vegetables",
            image = R.drawable.fruits_category,
            color = 0xFFF8A44C,
            products = products.filter { it.category == "Vegetables" }
        ),
        CategoryProducts(
            category = "Dairy",
            image = R.drawable.fruits_category,
            color = 0xFFF7A593,
            products = products.filter { it.category == "Dairy" }
        ),

    )

    val user = User(
        name = "Test User",
        email = "test123@gmail.com",
        image = R.drawable.screenshot
    )
}

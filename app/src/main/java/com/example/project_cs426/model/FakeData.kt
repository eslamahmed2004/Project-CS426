package com.example.project_cs426.model

import com.example.project_cs426.R
import com.example.project_cs426.pages.favourite.FavouriteItem

object FakeData {

    val products = listOf(
        Product(
            id = 1,
            name = "Organic Bananas",
            image = R.drawable.apple,
            price = 4.99,
            description = "Fresh organic bananas rich in nutrients.",
            weight = "1kg",
            category = "Fruit" ,
            brand = "Organic Farm"
        )
    )

    val sampleFavourites = listOf(
        FavouriteItem(1, "Sprite Can", "325ml, Price", 1.50, R.drawable.sprite_can),
        FavouriteItem(2, "Diet Coke", "355ml, Price", 1.99, R.drawable.diet_coke),
        FavouriteItem(3, "Apple & Grape Juice", "2L, Price", 15.50, R.drawable.apple_grape_juice),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(4, "Coca Cola Can", "325ml, Price", 4.99, R.drawable.coca_cola_can),
        FavouriteItem(5, "Pepsi Can", "330ml, Price", 4.99, R.drawable.pepsi_can)
    )

    val categories = listOf(
        CategoryProducts(
            category = "Fruits",
            image = R.drawable.fruits_category,
            color = 0xFF53B175,
            products = products.filter { it.category == "Fruit" }
        ),
        CategoryProducts(
            category = "Vegetables",
            image = R.drawable.fruits_category,
            color = 0xFFF8A44C,
            products = products.filter { it.category == "Fruit" }
        ),
        CategoryProducts(
            category = "Dairy",
            image = R.drawable.fruits_category,
            color = 0xFFF7A593,
            products = products.filter { it.category == "Fruit" }
        ),

    )

    val user = User(
        name = "Test User",
        email = "test123@gmail.com",
        image = R.drawable.screenshot
    )
}

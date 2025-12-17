package com.example.project_cs426.model

import com.example.project_cs426.R
import com.example.project_cs426.pages.favourite.FavouriteItemUi

/**
 * FakeData - adjusted to use categoryId (Int) which matches your Product model
 *
 * Mapping used:
 *  1 -> Fruits
 *  2 -> Vegetables
 *  3 -> Dairy
 */
object FakeData {

    val products = mutableListOf(
        Product(
            id = 1,
            name = "Organic Bananas",
            image = R.drawable.apple,
            price = 4.99,
            description = "Fresh organic bananas rich in nutrients.",
            weight = "1kg",
            brand = "Organic Farm",
            categoryId = 1
        ),
        Product(
            id = 2,
            name = "Red Apple",
            image = R.drawable.apple,
            price = 3.49,
            description = "Crispy red apples full of vitamins.",
            weight = "1kg",
            brand = "Nature Fresh",
            categoryId = 1
        ),
        Product(
            id = 3,
            name = "Orange",
            image = R.drawable.apple,
            price = 2.99,
            description = "Juicy oranges rich in vitamin C.",
            weight = "1kg",
            brand = "Citrus Valley",
            categoryId = 1
        ),
        Product(
            id = 4,
            name = "Tomato",
            image = R.drawable.apple,
            price = 1.99,
            description = "Fresh tomatoes perfect for cooking.",
            weight = "1kg",
            brand = "Green Farm",
            categoryId = 2
        ),
        Product(
            id = 5,
            name = "Potato",
            image = R.drawable.apple,
            price = 0.99,
            description = "High quality potatoes for daily use.",
            weight = "2kg",
            brand = "Farm House",
            categoryId = 2
        ),
        Product(
            id = 6,
            name = "Fresh Milk",
            image = R.drawable.apple,
            price = 5.99,
            description = "Pure fresh milk rich in calcium.",
            weight = "2L",
            brand = "Daily Milk",
            categoryId = 3
        ),
        Product(
            id = 7,
            name = "Cheese",
            image = R.drawable.apple,
            price = 9.99,
            description = "Delicious creamy cheese.",
            weight = "500g",
            brand = "Cheesy Delight",
            categoryId = 3
        )
    )

    // sample favourites must match FavouriteItemUi constructor:
    val sampleFavourites = mutableListOf(
        FavouriteItemUi(
            uid = null,
            productId = 1,
            name = "Sprite Can",
            subtitle = "325ml, Price",
            price = 1.50,
            imageRes = R.drawable.sprite_can
        ),
        FavouriteItemUi(
            uid = null,
            productId = 2,
            name = "Diet Coke",
            subtitle = "355ml, Price",
            price = 1.99,
            imageRes = R.drawable.diet_coke
        ),
        FavouriteItemUi(
            uid = null,
            productId = 3,
            name = "Apple & Grape Juice",
            subtitle = "2L, Price",
            price = 15.50,
            imageRes = R.drawable.apple_grape_juice
        ),
        FavouriteItemUi(
            uid = null,
            productId = 4,
            name = "Coca Cola Can",
            subtitle = "325ml, Price",
            price = 4.99,
            imageRes = R.drawable.coca_cola_can
        ),
        FavouriteItemUi(
            uid = null,
            productId = 5,
            name = "Pepsi Can",
            subtitle = "330ml, Price",
            price = 4.99,
            imageRes = R.drawable.pepsi_can
        )
    )

    val categories = mutableListOf(
        CategoryProducts(
            category = "Fruits",
            image = R.drawable.fruits_category,
            color = 0xFF53B175L,
            products = products.filter { it.categoryId == 1 }
        ),
        CategoryProducts(
            category = "Vegetables",
            image = R.drawable.fruits_category,
            color = 0xFFF8A44CL,
            products = products.filter { it.categoryId == 2 }
        ),
        CategoryProducts(
            category = "Dairy",
            image = R.drawable.fruits_category,
            color = 0xFFF7A593L,
            products = products.filter { it.categoryId == 3 }
        )
    )

    val user = User(
        name = "Test User",
        email = "test123@gmail.com",
        image = R.drawable.screenshot
    )
}

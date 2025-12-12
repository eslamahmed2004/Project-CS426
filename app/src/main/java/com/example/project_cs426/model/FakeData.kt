package com.example.project_cs426.model

import com.example.project_cs426.R

object FakeData {
    val products = listOf(
        Product(
            id = 1,
            name = "Natural Red Apple",
            image = R.drawable.apple,
            price = 4.99,
            description = "Apples are nutritious and may be good for weight loss. They contain fiber, vitamins, and antioxidants.",
            weight = "1kg",
            category = "Fruits",
            brand = "Nature's Best"
        ),
        Product(
            id = 2,
            name = "Fresh Banana",
            image = R.drawable.banana,
            price = 3.49,
            description = "Bananas are a great source of energy, rich in potassium and natural sugars.",
            weight = "1.2kg",
            category = "Fruits",
            brand = "Tropical Farms"
        ),

    )

    val user = User(
        name = "Test User",
        email = "test123@gmail.com",
        image = R.drawable.screenshot
    )
}

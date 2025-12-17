package com.example.project_cs426.model

data  class Product(
    val id: Int,
    val name: String,
    val image: Int,
    val price: Double,
    val description: String,
    val weight: String = "1kg",
    val brand: String,
    val categoryId: Int

)

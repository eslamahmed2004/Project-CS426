package com.example.project_cs426.model

import com.example.project_cs426.model.Product

data class CategoryProducts(
    val category: String,
    val image: Int,
    val color: Long,
    val products: List<Product>
)
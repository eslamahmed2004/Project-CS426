package com.example.project_cs426.model

import com.example.project_cs426.R

data class CartItem(
    val id: Int,
    val name: String,
    val subtitle: String,
    val price: Double,
    val imageRes: Int = R.drawable.ic_launcher_background,
    var quantity: Int = 1
)

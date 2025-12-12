package com.example.project_cs426.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project_cs426.model.Product
import com.example.project_cs426.repository.ProductRepository

open class ProductViewModel(
    private val repo: ProductRepository = ProductRepository()
) : ViewModel() {

    var product by mutableStateOf<Product?>(null)

    var quantity by mutableStateOf(1)

    var isFavorite by mutableStateOf(false)

    fun loadProduct(id: Int) {
        product = repo.getProductById(id)
    }

    open fun increase() { quantity++ }
    open fun decrease() { if (quantity > 1) quantity-- }

    open fun toggleFavorite() {
        isFavorite = !isFavorite
    }
}

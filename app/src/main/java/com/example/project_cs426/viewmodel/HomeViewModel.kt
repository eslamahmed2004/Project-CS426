package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.model.Product
import com.example.project_cs426.model.FakeData
import com.example.project_cs426.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _uiState.value = HomeUiState(products = FakeData.products)
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(
                productId = product.id,
                name = product.name,
                image = product.image,
                price = product.price
            )
        }
    }
}

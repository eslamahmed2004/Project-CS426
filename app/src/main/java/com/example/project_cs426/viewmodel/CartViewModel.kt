package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.local.entity.CartItemEntity
import com.example.project_cs426.repository.CartRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartItemEntity> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false
)

open class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            combine(
                repository.getAllCartItems(),
                repository.getTotalPrice()
            ) { items, total ->
                CartUiState(
                    items = items,
                    totalPrice = total ?: 0.0
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onQuantityChange(item: CartItemEntity, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(item)
            return
        }
        viewModelScope.launch {
            repository.updateQuantity(item, newQuantity)
        }
    }

    fun removeItem(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
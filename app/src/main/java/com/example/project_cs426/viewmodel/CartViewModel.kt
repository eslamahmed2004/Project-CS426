package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_cs426.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addItem(item: CartItem) {
        val current = _cartItems.value.toMutableList()
        val existingIndex = current.indexOfFirst { it.id == item.id }
        if (existingIndex != -1) {
            val oldItem = current[existingIndex]
            current[existingIndex] = oldItem.copy(quantity = oldItem.quantity + item.quantity)
        } else {
            current.add(item)
        }
        _cartItems.value = current
    }

    fun addAllItems(items: List<CartItem>) {
        val current = _cartItems.value.toMutableList()
        for (item in items) {
            val existingIndex = current.indexOfFirst { it.id == item.id }
            if (existingIndex != -1) {
                val oldItem = current[existingIndex]
                current[existingIndex] = oldItem.copy(quantity = oldItem.quantity + item.quantity)
            } else {
                current.add(item)
            }
        }
        _cartItems.value = current
    }

    fun removeItem(id: Int) {
        _cartItems.value = _cartItems.value.filter { it.id != id }
    }

    fun updateQuantity(id: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(id)
            return
        }
        val updated = _cartItems.value.map { item ->
            if (item.id == id) item.copy(quantity = newQuantity) else item
        }
        _cartItems.value = updated
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}

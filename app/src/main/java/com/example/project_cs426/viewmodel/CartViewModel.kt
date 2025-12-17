package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_cs426.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Add single item (merge by id)
    fun addItem(item: CartItem) {
        val current = _cartItems.value.toMutableList()
        val idx = current.indexOfFirst { it.id == item.id }
        if (idx != -1) {
            val old = current[idx]
            current[idx] = old.copy(quantity = old.quantity + item.quantity)
        } else {
            current.add(item)
        }
        _cartItems.value = current
    }

    // Add all (used by Favourite -> Add All To Cart)
    fun addAllItems(items: List<CartItem>) {
        val current = _cartItems.value.toMutableList()
        for (it in items) {
            val idx = current.indexOfFirst { ci -> ci.id == it.id }
            if (idx != -1) {
                val old = current[idx]
                current[idx] = old.copy(quantity = old.quantity + it.quantity)
            } else {
                current.add(it)
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
        _cartItems.value = _cartItems.value.map { if (it.id == id) it.copy(quantity = newQuantity) else it }
    }

    fun getTotalPrice(): Double = _cartItems.value.sumOf { it.price * it.quantity }
}

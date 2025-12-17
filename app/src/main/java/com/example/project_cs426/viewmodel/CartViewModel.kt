package com.example.project_cs426.pages.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlin.math.round

// UI model + fake data (استبدل بـ Room/Repository لاحقًا)
data class CartItemUi(
    val id: String,
    val name: String,
    val subtitle: String,
    val price: Double,
    val imageRes: Int,
    val quantity: Int = 1
)

object FakeCartData {
    val sample = listOf(
        CartItemUi(
            id = "1",
            name = "Bell Pepper Red",
            subtitle = "1kg, Price",
            price = 4.99,
            imageRes = com.example.project_cs426.R.drawable.pepper // عدّل الاسم حسب drawable عندك
        ),
        CartItemUi(
            id = "2",
            name = "Egg Chicken Red",
            subtitle = "4pcs, Price",
            price = 1.99,
            imageRes = com.example.project_cs426.R.drawable.eggs
        ),
        CartItemUi(
            id = "3",
            name = "Organic Bananas",
            subtitle = "12kg, Price",
            price = 3.00,
            imageRes = com.example.project_cs426.R.drawable.bananas
        ),
        CartItemUi(
            id = "4",
            name = "Ginger",
            subtitle = "250gm, Price",
            price = 2.99,
            imageRes = com.example.project_cs426.R.drawable.ginger
        )
    )
}

open class CartViewModel : ViewModel() {

    // MutableStateList so Compose recomposes when items change
    private val _items = mutableStateListOf<CartItemUi>().apply {
        addAll(FakeCartData.sample) // <-- استبدل أو احذف لو عايز البداية فاضية
    }
    open val items: List<CartItemUi> get() = _items

    open fun increaseQuantity(itemId: String) {
        val index = _items.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val it = _items[index]
            _items[index] = it.copy(quantity = it.quantity + 1)
        }
    }

    open fun decreaseQuantity(itemId: String) {
        val index = _items.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val it = _items[index]
            val newQty = (it.quantity - 1).coerceAtLeast(1)
            _items[index] = it.copy(quantity = newQty)
        }
    }

    open fun removeItem(itemId: String) {
        _items.removeAll { it.id == itemId }
    }

    /**
     * Adds a list of CartItemUi to the current cart.
     * If an item already exists, increment its quantity by newItem.quantity
     */
    fun addAllToCart(list: List<CartItemUi>) {
        list.forEach { newItem ->
            val idx = _items.indexOfFirst { it.id == newItem.id }
            if (idx == -1) _items.add(newItem)
            else {
                val old = _items[idx]
                _items[idx] = old.copy(quantity = old.quantity + newItem.quantity)
            }
        }
    }

    open fun totalPrice(): Double {
        val sum = _items.sumOf { it.price * it.quantity }
        return (round(sum * 100)) / 100.0
    }
}

package com.example.project_cs426.repository

import com.example.project_cs426.data.local.dao.CartDao
import com.example.project_cs426.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CartRepository(private val cartDao: CartDao) {

    fun getAllCartItems(): Flow<List<CartItemEntity>> =
        cartDao.getAllCartItems()

    fun getTotalPrice(): Flow<Double?> =
        cartDao.getTotalPrice()

    suspend fun addToCart(productId: Int, name: String, image: Int, price: Double) {

        val currentItems = cartDao.getAllCartItems().first()

        val existingItem = currentItems.find {
            it.productId == productId.toString()
        }

        if (existingItem != null) {
            cartDao.updateCartItem(
                existingItem.copy(quantity = existingItem.quantity + 1)
            )
        } else {
            val newItem = CartItemEntity(
                productId = productId.toString(),
                productName = name,
                productImage = image.toString(),
                price = price,
                quantity = 1
            )
            cartDao.insertCartItem(newItem)
        }
    }

    suspend fun updateQuantity(item: CartItemEntity, newQuantity: Int) {
        cartDao.updateCartItem(item.copy(quantity = newQuantity))
    }

    suspend fun removeFromCart(item: CartItemEntity) {
        cartDao.deleteCartItem(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}

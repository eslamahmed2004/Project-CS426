package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.R
import com.example.project_cs426.data.local.dao.ProductDao
import com.example.project_cs426.data.local.dao.UserDao
import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.data.mapper.toEntity
import com.example.project_cs426.data.mapper.toProduct
import com.example.project_cs426.model.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminViewModel(private val productDao: ProductDao,
                     private val userDao: UserDao
) : ViewModel() {



    // 🔹 كل المنتجات (من DB مباشرة)
    val products: StateFlow<List<Product>> =
        productDao.getAllProducts()
            .map { entities -> entities.map { it.toProduct() } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // 🔹 عدد المستخدمين
    val userCount: StateFlow<Int> =
        userDao.getUserCount()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    // 🔹 عدد المنتجات
    val productCount: StateFlow<Int> =
        productDao.getAllProducts()
            .map { it.size }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    fun addDummyProduct() {
        viewModelScope.launch {
            productDao.insertProduct(
                ProductEntity(
                    name = "Apple",
                    image = R.drawable.apple,
                    price = 3.5,
                    description = "Fresh Apple",
                    weight = "1kg",
                    category = "Fruits",
                    brand = "Local"
                )
            )
        }
    }


    // ➕ Add
    fun addProduct(product: Product) {
        viewModelScope.launch {
            productDao.insertProduct(product.toEntity())
        }
    }





    // ✏ Update
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productDao.updateProduct(product.toEntity())
        }
    }

    // 🗑 Delete
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.deleteProduct(product.toEntity())
        }
    }
}

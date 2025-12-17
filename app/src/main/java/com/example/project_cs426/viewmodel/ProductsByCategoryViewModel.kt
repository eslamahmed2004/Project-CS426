package com.example.project_cs426.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_cs426.data.local.dao.ProductDao

class ProductsByCategoryViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    fun getProducts(categoryName: String) =
        productDao.getProductsByCategoryName(categoryName)
}
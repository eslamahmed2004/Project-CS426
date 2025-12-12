package com.example.project_cs426.repository

import com.example.project_cs426.model.FakeData
import com.example.project_cs426.model.Product

class ProductRepository {

    fun getProductById(id: Int): Product? {
        return FakeData.products.find { it.id == id }
    }
}

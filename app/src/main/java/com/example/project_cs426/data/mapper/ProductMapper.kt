package com.example.project_cs426.data.mapper

import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.model.Product

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        image = image,
        price = price,
        description = description,
        weight = weight,
        brand = brand,
        categoryId = categoryId
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        image = image,
        price = price,
        description = description,
        weight = weight,
        brand = brand,
        categoryId = categoryId
    )
}

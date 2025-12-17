package com.example.project_cs426.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded val category: CategoryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<ProductEntity>
)

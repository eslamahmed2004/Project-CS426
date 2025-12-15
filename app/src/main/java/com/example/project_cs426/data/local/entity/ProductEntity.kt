package com.example.project_cs426.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: Int,
    val price: Double,
    val description: String,
    val weight: String,
    val category: String,
    val brand: String
)


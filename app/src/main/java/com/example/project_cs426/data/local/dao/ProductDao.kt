package com.example.project_cs426.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.model.ProductWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>


    @Query("""
    SELECT products.*, categories.name AS categoryName
    FROM products
    INNER JOIN categories ON products.categoryId = categories.id
    WHERE categories.name = :categoryName
""")
    fun getProductsByCategoryName(
        categoryName: String
    ): Flow<List<ProductWithCategory>>


    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    fun getProductsByCategory(categoryId: Int): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)}

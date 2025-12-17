package com.example.project_cs426.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_cs426.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    // ================== Read ==================

    @Query("SELECT * FROM favourites ORDER BY uid DESC")
    fun observeAll(): Flow<List<FavouriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE productId = :productId)")
    suspend fun exists(productId: Int): Boolean


    // ================== Write ==================

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)

    @Query("DELETE FROM favourites")
    suspend fun clear()
}

package com.example.project_cs426.data.local.dao

import androidx.room.*
import com.example.project_cs426.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO لجدول favourites
 *
 * يوفّر:
 * - observeAll() -> Flow لتحديثات تلقائية للـ UI
 * - getAll() -> قراءة لمرة واحدة (suspend)
 * - insert / insertAll
 * - deleteByProductId / delete(entity) / clear
 * - exists(productId) -> Boolean
 */
@Dao
interface FavouriteDao {

    // ================== Read ==================

    /** مراقبة كل المفضلات كـ Flow (مرتبة بأحدث إدخال أولاً) */
    @Query("SELECT * FROM favourites ORDER BY uid DESC")
    fun observeAll(): Flow<List<FavouriteEntity>>

    /** جلب كل العناصر لمرة واحدة (suspend) */
    @Query("SELECT * FROM favourites ORDER BY uid DESC")
    suspend fun getAll(): List<FavouriteEntity>

    /** هل المنتج موجود في المفضلة؟ (يعيد true/false) */
    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE productId = :productId)")
    suspend fun exists(productId: Int): Boolean


    // ================== Write ==================

    /** إدراج عنصر واحد. نتجاهل إذا كان productId موجود (بفضل الفهرس الفريد) */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavouriteEntity): Long

    /** إدراج مجموعة عناصر دفعة واحدة */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<FavouriteEntity>)

    /** حذف عنصر بحسب الـ productId */
    @Query("DELETE FROM favourites WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)

    /** حذف عبر entity */
    @Delete
    suspend fun delete(entity: FavouriteEntity)

    /** مسح كل المفضلات */
    @Query("DELETE FROM favourites")
    suspend fun clear()
}

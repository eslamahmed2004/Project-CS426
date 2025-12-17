package com.example.project_cs426.data.repository

import com.example.project_cs426.data.local.dao.FavouriteDao
import com.example.project_cs426.data.local.entity.FavouriteEntity
import com.example.project_cs426.pages.favourite.FavouriteItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepository(
    private val dao: FavouriteDao
) {

    /* ===================== Observe ===================== */

    /**
     * Observe favourites as UI models
     */
    fun observeFavourites(): Flow<List<FavouriteItemUi>> {
        return dao.observeAll()
            .map { entities ->
                entities.map { it.toUi() }
            }
    }

    /* ===================== Actions ===================== */

    /**
     * Add item to favourites (ignored if already exists)
     */
    suspend fun add(item: FavouriteItemUi) {
        dao.insert(item.toEntity())
    }

    /**
     * Remove favourite by productId
     */
    suspend fun deleteByProductId(productId: Int) {
        dao.deleteByProductId(productId)
    }

    /**
     * Toggle favourite state
     */
    suspend fun toggle(item: FavouriteItemUi) {
        val exists = dao.exists(item.productId)
        if (exists) {
            dao.deleteByProductId(item.productId)
        } else {
            dao.insert(item.toEntity())
        }
    }

    /**
     * Check if product is favourite
     */
    suspend fun isFavourite(productId: Int): Boolean {
        return dao.exists(productId)
    }

    /**
     * Clear all favourites
     */
    suspend fun clear() {
        dao.clear()
    }
}

/* ===================== Mappers ===================== */

/**
 * Entity -> UI
 */
private fun FavouriteEntity.toUi(): FavouriteItemUi =
    FavouriteItemUi(
        uid = this.uid,
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle ?: "", // fallback لو null
        price = this.price,
        imageRes = this.imageRes ?: android.R.drawable.ic_menu_report_image // fallback للصورة

    )

/**
 * UI -> Entity
 * uid MUST be 0 so Room auto-generates it
 */
private fun FavouriteItemUi.toEntity(): FavouriteEntity =
    FavouriteEntity(
        uid = 0L,                    // 👈 مهم جدًا
        productId = productId,
        name = name,
        subtitle = subtitle,
        price = price,
        imageRes = imageRes
    )

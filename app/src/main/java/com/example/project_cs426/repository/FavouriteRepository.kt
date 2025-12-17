package com.example.project_cs426.data.repository

import com.example.project_cs426.pages.favourite.FavouriteItemUi
import com.example.project_cs426.data.local.dao.FavouriteDao
import com.example.project_cs426.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepository(
    private val dao: FavouriteDao
) {

    /** يعيد Flow لقائمة FavouriteItemUi */
    fun observeFavourites(): Flow<List<FavouriteItemUi>> {
        // تأكد أن DAO لديه دالة observeAll() التي ترجع Flow<List<FavouriteEntity>>
        return dao.observeAll().map { list ->
            list.map { entity -> entity.toUi() }
        }
    }

    /** يضيف عنصر (suspend) */
    suspend fun add(item: FavouriteItemUi) {
        dao.insert(item.toEntity())
    }

    /** يحذف عنصر بحسب productId (suspend) */
    suspend fun deleteByProductId(productId: Int) {
        dao.deleteByProductId(productId)
    }

    /**
     * يقلب الحالة: لو موجود يحذفه وإلا يضيفه
     * يستخدم dao.exists(productId) لأن DAO عندك يوفر exists وليس findByProductId
     */
    suspend fun toggle(item: FavouriteItemUi) {
        val exists = dao.exists(item.productId)
        if (exists) {
            dao.deleteByProductId(item.productId)
        } else {
            dao.insert(item.toEntity())
        }
    }

    /** يرجع boolean لو العنصر مفضل أم لا (باستخدام exists) */
    suspend fun isFavourite(productId: Int): Boolean {
        return dao.exists(productId)
    }
}

/* ---- تحويلات مساعدة بين Entity و UI model ----
   إذا لديك Mapper class منفصل استخدمه بدلاً من هذي الدوال. */
private fun FavouriteEntity.toUi(): FavouriteItemUi {
    return FavouriteItemUi(
        uid = this.uid,
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle ?: "",
        price = this.price,
        imageRes = this.imageRes ?: android.R.drawable.ic_menu_report_image
    )
}

private fun FavouriteItemUi.toEntity(): FavouriteEntity {
    return FavouriteEntity(
        uid = this.uid,
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle,
        price = this.price,
        imageRes = this.imageRes
    )
}

package com.example.project_cs426.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.project_cs426.pages.favourite.FavouriteItemUi

@Entity(
    tableName = "favourites",
    indices = [
        Index(value = ["productId"], unique = true)
    ]
)
data class FavouriteEntity(

    @PrimaryKey(autoGenerate = true)
    val uid: Long? = null,          // 👈 auto generated

    val productId: Int,          // 👈 ID الحقيقي للمنتج

    val name: String,

    val subtitle: String?,

    val price: Double,

    val imageRes: Int?
)
fun FavouriteEntity.toUi(): FavouriteItemUi {
    return FavouriteItemUi(
        uid = this.uid,
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle ?: "", // fallback لو null
        price = this.price,
        imageRes = this.imageRes ?: android.R.drawable.ic_menu_report_image // fallback للصورة
    )
}
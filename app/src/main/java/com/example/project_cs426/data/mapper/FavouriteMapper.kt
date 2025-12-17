package com.example.project_cs426.data.local.mapper

import com.example.project_cs426.data.local.entity.FavouriteEntity
import com.example.project_cs426.pages.favourite.FavouriteItemUi

fun FavouriteEntity.toUi(): FavouriteItemUi {
    return FavouriteItemUi(
        uid = this.uid,
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle ?: "", // fallback لو nul
        price = this.price,
        imageRes = this.imageRes ?: android.R.drawable.ic_menu_report_image // fallback للصورة
    )
}

fun FavouriteItemUi.toEntity(): FavouriteEntity {
    return FavouriteEntity(
        uid = this.uid ?: 0L, // Room سيولّد uid إذا كان 0
        productId = this.productId,
        name = this.name,
        subtitle = this.subtitle,
        price = this.price,
        imageRes = this.imageRes
    )
}

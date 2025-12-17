package com.example.project_cs426.pages.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.project_cs426.data.repository.FavouriteRepository
//import com.example.project_cs426.pages.cart.CartItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * UI Model
 */
data class FavouriteItemUi(
    val uid: Long? = null,
    val productId: Int,
    val name: String,
    val subtitle: String = "",
    val price: Double = 0.0,
    val imageRes: Int = android.R.drawable.ic_menu_report_image
)

/**
 * FavouriteViewModel
 *
 * - يدعم التشغيل الحقيقي مع Room Repository
 * - يدعم Preview بدون Repo (حل سريع)
 */
class FavouriteViewModel(
    private val repo: FavouriteRepository? = null
) : ViewModel() {

    /* -------- Preview fallback -------- */
    private val previewFlow = MutableStateFlow(
        listOf(
            FavouriteItemUi(
                productId = 1,
                name = "Sprite Can",
                subtitle = "325ml, Price",
                price = 1.50,
                imageRes = android.R.drawable.ic_menu_gallery
            )
        )
    )

    /* -------- Public State -------- */
    val favourites: StateFlow<List<FavouriteItemUi>> =
        repo?.observeFavourites()
            ?.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
            ?: previewFlow

    /* -------- Actions -------- */

    fun addFavourite(item: FavouriteItemUi) {
        viewModelScope.launch {
            repo?.add(item)
                ?: run {
                    previewFlow.value = previewFlow.value + item
                }
        }
    }

    fun removeByProductId(productId: Int) {
        viewModelScope.launch {
            repo?.deleteByProductId(productId)
                ?: run {
                    previewFlow.value =
                        previewFlow.value.filterNot { it.productId == productId }
                }
        }
    }

    fun toggleFavourite(item: FavouriteItemUi) {
        viewModelScope.launch {
            repo?.toggle(item)
        }
    }

    suspend fun isFavourite(productId: Int): Boolean {
        return repo?.isFavourite(productId) ?: false
    }

    /* -------- Favourite → Cart -------- */
//    fun toCartItems(list: List<FavouriteItemUi>): List<CartItemUi> {
//        return list.map { fav ->
//            CartItemUi(
//                id = fav.productId,
//                name = fav.name,
//                subtitle = fav.subtitle,
//                price = fav.price,
//                imageRes = fav.imageRes,
//                quantity = 1
//            )
//        }
//    }
}

/**
 * Factory (عند التشغيل الحقيقي بدون Hilt)
 */
class FavouritesViewModelFactory(
    private val repo: FavouriteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

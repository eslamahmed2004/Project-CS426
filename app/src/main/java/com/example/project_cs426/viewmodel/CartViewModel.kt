package com.example.project_cs426.pages.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.math.round

/**
 * Simple UI model for cart items.
 * إذا عندك تعريف مشابه في مشروعك احذف هذا وتعامل مع الموجود.
 */
data class CartItemUi(
    val id: Int,               // معرف المنتج (productId)
    val name: String,
    val subtitle: String = "",
    val price: Double = 0.0,
    val imageRes: Int = 0,
    val quantity: Int = 1
)

/**
 * CartViewModel
 *
 * - يحفظ العناصر في mutableStateListOf حتى تعيد Compose الرندر تلقائياً
 * - يوفر وظائف لإضافة/حذف/تعديل واحتساب الإجمالي
 */
open class CartViewModel : ViewModel() {

    // الحالة الداخلية للمحتويات (Compose ستعيد الرسم عند التغيير)
    private val _items = mutableStateListOf<CartItemUi>()
    open val items: List<CartItemUi> get() = _items

    /* ---------------- Basic actions ---------------- */

    /**
     * أضف عنصر واحد (إذا موجود دمج الكميات)
     */
    fun addItem(item: CartItemUi) {
        val idx = _items.indexOfFirst { it.id == item.id }
        if (idx == -1) {
            _items.add(item)
        } else {
            val old = _items[idx]
            _items[idx] = old.copy(quantity = old.quantity + item.quantity)
        }
    }

    /**
     * أضف مجموعة عناصر، وادمج الكميات للعناصر المتكررة
     */
    fun addAllToCart(list: List<CartItemUi>) {
        viewModelScope.launch {
            list.forEach { newItem ->
                val idx = _items.indexOfFirst { it.id == newItem.id }
                if (idx == -1) {
                    _items.add(newItem)
                } else {
                    val old = _items[idx]
                    _items[idx] = old.copy(quantity = old.quantity + newItem.quantity)
                }
            }
        }
    }

    /**
     * زيادة كمية العنصر بمقدار واحد
     */
    open fun increaseQuantity(itemId: Int) {
        val idx = _items.indexOfFirst { it.id == itemId }
        if (idx != -1) {
            val it = _items[idx]
            _items[idx] = it.copy(quantity = it.quantity + 1)
        }
    }

    /**
     * نقصان كمية العنصر بمقدار واحد (لا يقل عن 1)
     */
    open  fun decreaseQuantity(itemId: Int) {
        val idx = _items.indexOfFirst { it.id == itemId }
        if (idx != -1) {
            val it = _items[idx]
            val newQty = (it.quantity - 1).coerceAtLeast(1)
            _items[idx] = it.copy(quantity = newQty)
        }
    }

    /**
     * إزالة عنصر من السلة
     */
   open fun removeItem(itemId: Int) {
        _items.removeAll { it.id == itemId }
    }

    /**
     * إفراغ السلة
     */
    fun clearCart() {
        _items.clear()
    }

    /* ---------------- Helpers / totals ---------------- */

    /**
     * إجمالي السعر (مقرب إلى خانتين عشريتين)
     */
    open fun totalPrice(): Double {
        val sum = _items.sumOf { it.price * it.quantity }
        return (round(sum * 100)) / 100.0
    }

    /**
     * للحصول على إجمالي كسلسلة (مثلاً لزر Checkout)
     */
    fun totalPriceFormatted(): String {
        return "$${"%.2f".format(totalPrice())}"
    }
}

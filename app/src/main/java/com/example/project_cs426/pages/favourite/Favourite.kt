package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.project_cs426.model.CartItem
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.R
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import kotlinx.coroutines.launch

data class FavouriteItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: String,
    val drawableRes: Int
)

private val sampleFavourites = listOf(
    FavouriteItem(1, "Sprite Can", "325ml, Price", "$1.50", R.drawable.sprite_can),
    FavouriteItem(2, "Diet Coke", "355ml, Price", "$1.99", R.drawable.diet_coke),
    FavouriteItem(3, "Apple & Grape Juice", "2L, Price", "$15.50", R.drawable.apple_grape_juice),
    FavouriteItem(4, "Coca Cola Can", "325ml, Price", "$4.99", R.drawable.coca_cola_can),
    FavouriteItem(5, "Pepsi Can", "330ml, Price", "$4.99", R.drawable.pepsi_can)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favourite(
    cartViewModel: CartViewModel? = null,
    onNavigateTo: (String) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val cartVm: CartViewModel = cartViewModel ?: viewModel()

    Scaffold(
        topBar = { FavouriteTopBar() },
        bottomBar = { FavouriteBottomBar(onNavigateTo) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(sampleFavourites) { item ->
                    FavouriteRow(item)
                    Divider(color = androidx.compose.ui.graphics.Color(0xFFE0E0E0))
                }
            }

            Button(
                onClick = {
                    val itemsToAdd = sampleFavourites.map { fav ->
                        CartItem(
                            id = fav.id,
                            name = fav.title,
                            subtitle = fav.subtitle,
                            price = fav.price.replace("$", "").toDoubleOrNull() ?: 0.0,
                            imageRes = fav.drawableRes,
                            quantity = 1
                        )
                    }

                    try {
                        cartVm.addAllItems(itemsToAdd)
                    } catch (e: Exception) {
                        scope.launch {
                            itemsToAdd.forEach { cartVm.addItem(it) }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF4CAF50))
            ) {
                Text("Add All To Cart", color = androidx.compose.ui.graphics.Color.White, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Favourite",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Black
            )
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Search, contentDescription = "Search") }
            IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
        }
    )
}

@Composable
private fun FavouriteRow(item: FavouriteItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO navigate */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(item.drawableRes),
            contentDescription = item.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(45.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(item.subtitle, fontSize = 13.sp, color = androidx.compose.ui.graphics.Color(0xFFA1A1A1))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(item.price, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.width(8.dp))

        Icon(Icons.Default.ArrowForward, contentDescription = "Go")
    }
}

@Composable
private fun FavouriteBottomBar(onNavigateTo: (String) -> Unit) {
    NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Shop") },
            selected = false,
            onClick = { onNavigateTo(Routes.startPage) },
            label = { Text("Shop") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Explore") },
            selected = false,
            onClick = { onNavigateTo("explore") },
            label = { Text("Explore") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
            selected = false,
            onClick = { onNavigateTo(Routes.cart) },
            label = { Text("Cart") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favourite") },
            selected = true,
            onClick = { onNavigateTo(Routes.favourite) },
            label = { Text("Favourite") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account") },
            selected = false,
            onClick = { onNavigateTo(Routes.Account) },
            label = { Text("Account") }
        )
    }
}

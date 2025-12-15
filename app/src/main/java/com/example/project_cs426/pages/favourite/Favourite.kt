package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.R
import com.example.project_cs426.model.CartItem
import com.example.project_cs426.viewmodel.CartViewModel
import kotlinx.coroutines.launch


data class FavouriteItem(val id: Int, val title: String, val subtitle: String, val price: String, val drawableRes: Int)

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
    cartViewModel: CartViewModel = viewModel(),
    onNavigateTo: (route: String) -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Favourite", fontSize = 20.sp) })
    }, bottomBar = {
        BottomAppBar { /* could reuse bottom nav */ }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(sampleFavourites) { item ->
                    FavouriteRow(item = item, onClick = { /* open details */ })
                    Divider()
                }
            }

            Button(onClick = {
                val itemsToAdd = sampleFavourites.map { fav ->
                    CartItem(
                        id = fav.id,
                        name = fav.title,
                        subtitle = fav.subtitle,
                        price = fav.price.replace("$","").toDoubleOrNull() ?: 0.0,
                        imageRes = fav.drawableRes,
                        quantity = 1
                    )
                }
                try {
                    cartViewModel.addAllItems(itemsToAdd)
                } catch (e: Exception) {
                    scope.launch {
                        itemsToAdd.forEach { cartViewModel.addItem(it) }
                    }
                }
            }, modifier = Modifier.fillMaxWidth().padding(20.dp).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) {
                Text("Add All To Cart", color = Color.White)
            }
        }
    }
}

@Composable
private fun FavouriteRow(item: FavouriteItem, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(item.drawableRes), contentDescription = item.title, modifier = Modifier.size(45.dp))
        Spacer(Modifier.width(12.dp))
        Column { Text(item.title); Text(item.subtitle, fontSize = 13.sp, color = Color(0xFFA1A1A1)) }
        Spacer(Modifier.weight(1f))
        Text(item.price)
        Spacer(Modifier.width(8.dp))
        Icon(Icons.Default.ArrowForward, contentDescription = "Go")
    }
}

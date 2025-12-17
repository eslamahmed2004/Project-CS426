package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.R
import com.example.project_cs426.model.FakeData.sampleFavourites
import com.example.project_cs426.viewmodel.CartViewModel
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

data class FavouriteItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: Double,
    val drawableRes: Int
)
@Composable
fun Favourite(
    cartViewModel: CartViewModel? = null,
    onNavigateTo: (String) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val cartVm: CartViewModel = cartViewModel ?: viewModel()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Favourite",
                color = Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(sampleFavourites) { item ->
            FavouriteRow(item)
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val itemsToAdd = sampleFavourites.map { fav ->
                        CartItem(
                            id = fav.id,
                            name = fav.title,
                            subtitle = fav.subtitle,
                            price = fav.price,
                            imageRes = fav.drawableRes,
                            quantity = 1
                        )
                    }

//                    try {
//                        cartVm.addAllItems(itemsToAdd)
//                    } catch (e: Exception) {
//                        scope.launch {
//                            itemsToAdd.forEach { cartVm.addItem(it) }
//                        }
//                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                colors = ButtonDefaults.buttonColors(PrimaryGreen)
            ) {
                Text(
                    "Add All To Cart",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
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

        Spacer(modifier = Modifier.width(12.dp))

        Column{
            Text(item.title, style = MaterialTheme.typography.bodyLarge, color = Black, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = MatteGray)
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(text = "$${item.price}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Black)

        Spacer(modifier = Modifier.width(8.dp))

        Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Go", modifier = Modifier.size(16.dp))
    }
}
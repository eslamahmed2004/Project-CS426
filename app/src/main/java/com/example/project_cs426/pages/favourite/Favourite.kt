package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.WhiteGray
import com.example.project_cs426.navigation.BottomBar

@Composable
fun Favourite(
    navController: NavController,
    items: List<FavouriteItemUi>,
    onRemove: (productId: Int) -> Unit,
    onAddAllToCart: () -> Unit
) {
    Scaffold(
        topBar = { FavouriteTopBar() },
        bottomBar = {
            Column {
                FavouriteBottomBar(onAddAll = onAddAllToCart)
                // central bottom bar (shared)
                BottomBar(navController)
            }
        },
        containerColor = Color(0xFFF7F7F7)
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favourites yet", color = DarkGray)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 166.dp, top = 8.dp) // leave space for bottom area
        ) {
            items(items = items, key = { it.productId }) { fav ->
                FavouriteItemRow(
                    item = fav,
                    onClick = { navController.navigate("product/${fav.productId}") },
                    onRemove = { onRemove(fav.productId) }
                )
                Divider(color = Color(0xFFECECEC), thickness = 1.dp)
            }
        }
    }
}

/* runtime composable using ViewModel */
@Composable
fun Favourite(
    navController: NavController,
    favouritesViewModel: FavouriteViewModel,
    onAddAllToCart: (List<FavouriteItemUi>) -> Unit
) {
    val items by favouritesViewModel.favourites.collectAsState()

    Favourite(
        navController = navController,
        items = items,
        onRemove = { productId -> favouritesViewModel.removeByProductId(productId) },
        onAddAllToCart = { onAddAllToCart(items) }
    )
}

/* ---------------- Missing helpers added below ---------------- */

/** Top app bar (centered title) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Favourite",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

/** Bottom area with "Add All To Cart" button */
@Composable
private fun FavouriteBottomBar(onAddAll: () -> Unit) {
    Surface(shadowElevation = 6.dp, color = Color.White) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Divider(color = Color(0xFFECECEC), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onAddAll,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Add All To Cart", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/** Single favourite row (image, title, subtitle, price, chevron) */
@Composable
private fun FavouriteItemRow(
    item: FavouriteItemUi,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image
        val imgSize = 44.dp
        Box(modifier = Modifier.size(imgSize)) {
            if (item.imageRes != null) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imgSize)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(imgSize)
                        .clip(CircleShape)
                        .background(WhiteGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.name.firstOrNull()?.toString() ?: "?", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title + subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = DarkGray,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Price + chevron
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${"%.2f".format(item.price)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            // small remove icon (calls onRemove)
            IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove", tint = Color(0xFF9E9E9E))
            }
        }
    }
}

/* ---------------- Previews ---------------- */

@Preview(showBackground = true, widthDp = 320, heightDp = 780)
@Composable
fun FavouritePreview() {
    val nav = rememberNavController()

    // sample items
    val previewItems = listOf(
        FavouriteItemUi(productId = 1, name = "Sprite Can", subtitle = "325ml, Price", price = 1.50, imageRes = android.R.drawable.ic_menu_gallery),
        FavouriteItemUi(productId = 2, name = "Diet Coke", subtitle = "355ml, Price", price = 1.99, imageRes = android.R.drawable.ic_menu_gallery),
        FavouriteItemUi(productId = 3, name = "Apple & Grape Juice", subtitle = "2L, Price", price = 15.50, imageRes = android.R.drawable.ic_menu_gallery),
        FavouriteItemUi(productId = 4, name = "Coca Cola Can", subtitle = "325ml, Price", price = 4.99, imageRes = android.R.drawable.ic_menu_gallery),
        FavouriteItemUi(productId = 5, name = "Pepsi Can", subtitle = "330ml, Price", price = 4.99, imageRes = android.R.drawable.ic_menu_gallery)
    )

    Favourite(
        navController = nav,
        items = previewItems,
        onRemove = {},
        onAddAllToCart = {}
    )
}

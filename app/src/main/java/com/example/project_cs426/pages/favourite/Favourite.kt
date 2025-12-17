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
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.MenuBook
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

/**
 * Favourite screen — arrow on the right navigates to ProductDetails (route: productDetails/{productId})
 *
 * Note: AppNavGraph registers product details under PRODUCT_DETAILS = "productDetails/{productId}".
 * So we navigate using "productDetails/<id>".
 * See AppNavGraph for how ProductViewModel loads the product on navigation. :contentReference[oaicite:1]{index=1}
 */

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
                FavouriteBottomNavigation() // custom bottom nav (kept as in your file)
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
            contentPadding = PaddingValues(bottom = 166.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(items = items, key = { it.productId }) { fav ->
                // pass a lambda that navigates to the correct product details route
                FavouriteItemRow(
                    item = fav,
                    onArrowClick = { navController.navigate("productDetails/${fav.productId}") },
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

/* ---------------- UI pieces ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Favourite",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

@Composable
private fun FavouriteBottomBar(onAddAll: () -> Unit) {
    Surface(shadowElevation = 6.dp, color = Color.White) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Divider(color = Color(0xFFECECEC), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Button(
                    onClick = onAddAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(text = "Add All To Cart", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/** Row layout for a single favourite item.
 *  The arrow on the right triggers navigation via onArrowClick.
 */
@Composable
private fun FavouriteItemRow(
    item: FavouriteItemUi,
    onArrowClick: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image left
        val imgSize = 56.dp
        Box(modifier = Modifier.size(imgSize)) {
            if (item.imageRes != null) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imgSize)
                        .clip(RoundedCornerShape(10.dp))
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

        Spacer(modifier = Modifier.width(14.dp))

        // Text column: name / price / subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${"%.2f".format(item.price)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = DarkGray,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // remove icon (small X)
        IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Remove", tint = Color(0xFF9E9E9E))
        }

        // arrow (chevron) — this is the navigation control
        IconButton(onClick = onArrowClick, modifier = Modifier.size(36.dp)) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Open", tint = DarkGray)
        }
    }
}

/* ---------------- Bottom navigation (kept as before) ---------------- */
@Composable
private fun FavouriteBottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(icon = Icons.Default.Storefront, label = "Shop")
        BottomNavItem(icon = Icons.Default.Home, label = "Home")
        BottomNavItem(icon = Icons.Default.Search, label = "Explore")
        BottomNavItem(icon = Icons.Outlined.MenuBook, label = "Courses")
        BottomNavItem(icon = Icons.Default.ShoppingCart, label = "Cart")
        BottomNavItem(icon = Icons.Default.Favorite, label = "Favourite", active = true)
        BottomNavItem(icon = Icons.Default.Person, label = "Account")
        BottomNavItem(icon = Icons.Outlined.AccountCircle, label = "Profile")
    }
}

@Composable
private fun BottomNavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean = false) {
    val tint = if (active) PrimaryGreen else DarkGray
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, tint = tint)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = tint)
    }
}

/* ---------------- Preview ---------------- */
@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun FavouritePreview() {
    val nav = rememberNavController()
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

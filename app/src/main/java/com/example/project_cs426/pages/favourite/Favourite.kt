package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.DarkGray

@Composable
fun Favourite(
    navController: NavController,
    favouritesViewModel: FavouritesViewModel = viewModel(),
    onAddAllToCart: (List<FavouriteItemUi>) -> Unit = {}
) {
    val items = favouritesViewModel.items

    Scaffold(
        topBar = { FavouriteTopBar() },
        bottomBar = {
            if (items.isNotEmpty()) {
                FavouriteBottomBar(
                    totalCount = items.size,
                    onAddAll = {
                        // نحصل على قائمة العناصر من الـ ViewModel ونمرّرها للخارج
                        val list = favouritesViewModel.addAll()
                        onAddAllToCart(list)
                    }
                )
            }
        }
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
            contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
        ) {
            items(items, key = { it.id }) { fav ->
                FavouriteItem(
                    item = fav,
                    onClick = {
                        // open product details (route name must match your nav graph)
                        navController.navigate("product/${fav.id}")
                    },
                    onRemove = {
                        favouritesViewModel.removeItem(fav.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Favourite",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
private fun FavouriteBottomBar(totalCount: Int, onAddAll: () -> Unit) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Column {
            Divider(thickness = 0.5.dp, color = MatteGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onAddAll() }, // استدعاء الـ lambda الممررة من الخارج
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text(text = "Add All To Cart", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Divider(thickness = 0.5.dp, color = MatteGray)
            BottomNavigationBarMock()
        }
    }
}

@Composable
private fun BottomNavigationBarMock() {
    // Visual mimic for bottom nav (replace with your real bottom nav if needed)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.Storefront, contentDescription = "Shop", tint = DarkGray)
            Text(text = "Shop", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.Search, contentDescription = "Explore", tint = DarkGray)
            Text(text = "Explore", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart, contentDescription = "Cart", tint = DarkGray)
            Text(text = "Cart", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.Favorite, contentDescription = "Favourite", tint = PrimaryGreen)
            Text(text = "Favourite", style = MaterialTheme.typography.bodySmall, color = PrimaryGreen)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = androidx.compose.material.icons.Icons.Default.Person, contentDescription = "Account", tint = DarkGray)
            Text(text = "Account", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
    }
}

/* ---------------- Previews ---------------- */

@Preview(showBackground = true)
@Composable
fun FavouriteItemRowPreview() {
    // use android drawable in preview to avoid missing project drawables
    val previewItem = FavouriteItemUi(
        id = "preview",
        name = "Sprite Can",
        subtitle = "325ml, Price",
        price = 1.50,
        imageRes = android.R.drawable.ic_menu_gallery
    )

    FavouriteItem(item = previewItem, onClick = {}, onRemove = {})
}

@Preview(showBackground = true)
@Composable
fun FavouritePreview() {
    val dummyNav = rememberNavController()
    val vm = FavouritesViewModel()
    Favourite(
        navController = dummyNav,
        favouritesViewModel = vm,
        onAddAllToCart = { /* preview no-op */ }
    )
}

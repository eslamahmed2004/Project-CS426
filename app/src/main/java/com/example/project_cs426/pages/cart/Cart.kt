package com.example.project_cs426.pages.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.pages.checkout.Checkout
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.ProjectCS426Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val items = cartViewModel.items
    val total = cartViewModel.totalPrice()

    // Modal sheet state
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }

    // show/hide based on showSheet
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            // Content of the checkout sheet
            Checkout(
                total = total,
                onDismiss = { scope.launch { sheetState.hide(); showSheet = false } },
                onPlaceOrder = {
                    // Example: clear cart and navigate to order success (or do network call)
                    // For now we'll clear cart and close sheet
                    // If you have an order flow, replace this with placeOrder() and navigate
                    // cartViewModel.clear() // implement if you want
                    scope.launch {
                        sheetState.hide()
                        showSheet = false
                        // Optionally clear cart:
                        // cartViewModel.clearAll() // if you implement this
                        // Navigate to a success screen if exists:
                        // navController.navigate("order_success")
                    }
                }
            )
        }
    }

    Scaffold(
        topBar = { CartTopBar() },
        bottomBar = {
            CartBottomBar(total = total, onCheckout = {
                // Open the checkout sheet
                scope.launch {
                    showSheet = true
                    sheetState.show()
                }
            })
        }
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Your cart is empty", color = DarkGray)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp)
        ) {
            items(items, key = { it.id }) { item ->
                CartItem(
                    item = item,
                    onIncrease = { cartViewModel.increaseQuantity(item.id) },
                    onDecrease = { cartViewModel.decreaseQuantity(item.id) },
                    onRemove = { cartViewModel.removeItem(item.id) },
                    onItemClick = {
                        navController.navigate("product/${item.id}")
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
private fun CartBottomBar(total: Double, onCheckout: () -> Unit) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = androidx.compose.ui.graphics.Color.White
    ) {
        Column {
            Divider(thickness = 0.5.dp, color = MatteGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onCheckout,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text(text = "Go to Checkout", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.width(12.dp))

                // small total pill
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$${String.format("%.2f", total)}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Divider(thickness = 0.5.dp, color = MatteGray)
            BottomNavigationBar()
        }
    }
}

@Composable
private fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Storefront, contentDescription = "Shop", tint = DarkGray)
            Text(text = "Shop", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Explore", tint = DarkGray)
            Text(text = "Explore", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart", tint = PrimaryGreen)
            Text(text = "Cart", style = MaterialTheme.typography.bodySmall, color = PrimaryGreen)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favourite", tint = DarkGray)
            Text(text = "Favourite", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Account", tint = DarkGray)
            Text(text = "Account", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CartPreview() {
    ProjectCS426Theme {
        Cart(
            navController = rememberNavController()
        )
    }
}

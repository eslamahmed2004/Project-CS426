package com.example.project_cs426.pages.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.project_cs426.navigation.BottomBar

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    // items is expected to be a Compose-observed list (mutableStateListOf or StateFlow collected)
    val items = cartViewModel.items
    val total = cartViewModel.totalPrice()

    // Bottom sheet state for Checkout
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Checkout(
                total = total,
                onDismiss = { scope.launch { sheetState.hide(); showSheet = false } },
                onPlaceOrder = {
                    scope.launch {
                        sheetState.hide()
                        showSheet = false
                        // If you want to call placeOrder on ViewModel:
                        // cartViewModel.placeOrder { success -> ... }
                    }
                }
            )
        }
    }

    Scaffold(
        topBar = { CartTopBar() },
        bottomBar = {
            CartBottomBar(
                total = total,
                onCheckout = {
                    scope.launch {
                        showSheet = true
                        sheetState.show()
                    }
                },
                navController = navController // <-- pass navController so BottomBar can show
            )
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
                Text(text = "Your cart is empty", color = DarkGray)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 140.dp, top = 8.dp)
        ) {
            items(items, key = { it.id }) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { cartViewModel.increaseQuantity(item.id) },
                    onDecrease = { cartViewModel.decreaseQuantity(item.id) },
                    onRemove = { cartViewModel.removeItem(item.id) },
                    onItemClick = { navController.navigate("product/${item.id}") }
                )
                Divider(color = Color(0xFFECECEC), thickness = 1.dp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

/* ----------------- Cart item row ----------------- */
@Composable
fun CartItemRow(
    item: CartItemUi,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    onItemClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF2F2F2)),
                contentAlignment = Alignment.Center
            ) {
                runCatching {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(56.dp)
                    )
                }.getOrElse {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // name + subtitle + qty selector
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkGray
                )
                Spacer(modifier = Modifier.height(10.dp))

                // quantity selector — calls the lambdas supplied by parent (which call the ViewModel)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        tonalElevation = 2.dp,
                        color = Color(0xFFF2F2F2)
                    ) {
                        TextButton(onClick = onDecrease, contentPadding = PaddingValues(0.dp), modifier = Modifier.fillMaxSize()) {
                            Text("-", style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(text = item.quantity.toString(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.width(12.dp))

                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        tonalElevation = 2.dp,
                        color = PrimaryGreen.copy(alpha = 0.12f)
                    ) {
                        TextButton(onClick = onIncrease, contentPadding = PaddingValues(0.dp), modifier = Modifier.fillMaxSize()) {
                            Text("+", color = PrimaryGreen, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // price column
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${"%.2f".format(item.price * item.quantity)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // remove 'x' at top-right corner of the item card
        IconButton(
            onClick = onRemove,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp)
                .size(34.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color(0xFF9E9E9E)
            )
        }
    }
}

/* ----------------- Bottom area ----------------- */
@Composable
private fun CartBottomBar(total: Double, onCheckout: () -> Unit, navController: NavController) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Column {
            Divider(thickness = 0.5.dp, color = MatteGray)
            Spacer(modifier = Modifier.height(8.dp))

            // Button that contains label + total pill on the right
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Button(
                    onClick = onCheckout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Go to Checkout", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
                        Spacer(modifier = Modifier.weight(1f))
                        // total pill
                        Surface(
                            tonalElevation = 0.dp,
                            color = Color(0xFF2D8F5A),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Box(modifier = Modifier.padding(horizontal = 12.dp), contentAlignment = Alignment.Center) {
                                Text(text = "$${"%.2f".format(total)}", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(thickness = 0.5.dp, color = MatteGray)

            // centralized bottom bar (navigation)
            BottomBar(navController)
        }
    }
}

/* ---------------- Previews ---------------- */

/* ---------------- Previews ---------------- */

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CartPreview() {
    ProjectCS426Theme {
        val fakeItems = listOf(
            CartItemUi(id = 1, name = "Bell Pepper Red", subtitle = "1kg, Price", price = 4.99, imageRes = android.R.drawable.ic_menu_gallery, quantity = 1),
            CartItemUi(id = 2, name = "Egg Chicken Red", subtitle = "4pcs, Price", price = 1.99, imageRes = android.R.drawable.ic_menu_gallery, quantity = 1),
            CartItemUi(id = 3, name = "Organic Bananas", subtitle = "12kg, Price", price = 3.00, imageRes = android.R.drawable.ic_menu_gallery, quantity = 1),
            CartItemUi(id = 4, name = "Ginger", subtitle = "250gm, Price", price = 2.99, imageRes = android.R.drawable.ic_menu_gallery, quantity = 1)
        )

        val nav = rememberNavController()

        // Preview host that provides a lightweight fake ViewModel implementation
        CartPreviewHost(nav = nav, items = fakeItems)
    }
}

@Composable
private fun CartPreviewHost(nav: NavController, items: List<CartItemUi>) {
    // local mutable copy of items for preview interactivity
    var localItems by remember { mutableStateOf(items) }

    // Fake vm that forwards operations to localItems so the UI updates in preview
    val fakeVm = remember {
        object : CartViewModel() {
            // override items to return our localItems
            override val items: List<CartItemUi>
                get() = localItems

            override fun increaseQuantity(itemId: Int) {
                localItems = localItems.map { if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it }
            }

            override fun decreaseQuantity(itemId: Int) {
                localItems = localItems.mapNotNull {
                    if (it.id == itemId) {
                        val newQty = it.quantity - 1
                        if (newQty <= 0) null else it.copy(quantity = newQty)
                    } else it
                }
            }

            override fun removeItem(itemId: Int) {
                localItems = localItems.filterNot { it.id == itemId }
            }

            override fun totalPrice(): Double = localItems.sumOf { it.price * it.quantity }
        }
    }

    Cart(
        navController = nav,
        cartViewModel = fakeVm
    )
}

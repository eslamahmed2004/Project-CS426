package com.example.project_cs426.pages.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.model.CartItem
import com.example.project_cs426.viewmodel.CartViewModel

private val PrimaryGreen = Color(0xFF53B175)
private val PrimaryText = Color(0xFF111827)
private val SecondaryText = Color(0xFF6B7280)
private val DividerColor = Color(0xFFE5E7EB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    cartViewModel: CartViewModel = viewModel(),
    onNavigateTo: (route: String) -> Unit = {}
) {
    val items by cartViewModel.cartItems.collectAsState()
    val totalText by remember(items) { derivedStateOf { String.format("$%.2f", cartViewModel.getTotalPrice()) } }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Cart", fontSize = 18.sp) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            CartBottomBar(onNavigateTo)
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items) { item ->
                    CartRow(
                        item = item,
                        onIncrease = { cartViewModel.updateQuantity(item.id, item.quantity + 1) },
                        onDecrease = {
                            if (item.quantity > 1) cartViewModel.updateQuantity(item.id, item.quantity - 1)
                            else cartViewModel.removeItem(item.id)
                        },
                        onRemove = { cartViewModel.removeItem(item.id) }
                    )
                    Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { onNavigateTo(Routes.checkout) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Go to Checkout", color = Color.White)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.height(56.dp).widthIn(min = 72.dp).clip(RoundedCornerShape(12.dp)).background(PrimaryGreen), contentAlignment = Alignment.Center) {
                    Text(totalText, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun CartRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().height(88.dp).padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = item.imageRes), contentDescription = item.name, modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)

        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(item.name, fontSize = 14.sp, color = PrimaryText)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.subtitle, fontSize = 12.sp, color = SecondaryText)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp).border(1.dp, DividerColor, CircleShape)) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text(item.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp).clip(CircleShape).background(PrimaryGreen)) {
                Icon(Icons.Default.Add, contentDescription = "Increase", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onRemove) { Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color(0xFF9CA3AF)) }

        Text(String.format("$%.2f", item.price), fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
private fun CartBottomBar(onNavigateTo: (route: String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = false, onClick = { onNavigateTo(Routes.home) }, icon = { Icon(Icons.Default.Home, contentDescription = "Shop") }, label = { Text("Shop") })
        NavigationBarItem(selected = false, onClick = { onNavigateTo(Routes.Explorer) }, icon = { Icon(Icons.Default.Search, contentDescription = "Explore") }, label = { Text("Explore") })
        NavigationBarItem(selected = true, onClick = { onNavigateTo(Routes.cart) }, icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }, label = { Text("Cart") })
        NavigationBarItem(selected = false, onClick = { onNavigateTo(Routes.favourite) }, icon = { Icon(Icons.Default.Favorite, contentDescription = "Favourite") }, label = { Text("Favourite") })
        NavigationBarItem(selected = false, onClick = { onNavigateTo(Routes.Account) }, icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account") }, label = { Text("Account") })
    }
}

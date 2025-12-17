package com.example.project_cs426.pages.cart

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.project_cs426.R
import com.example.project_cs426.data.local.AppDatabase
import com.example.project_cs426.data.local.entity.CartItemEntity
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.repository.CartRepository
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.Silver
import com.example.project_cs426.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    viewModel: CartViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as Application)
                val db = AppDatabase.getInstance(app)
                CartViewModel(CartRepository(db.cartDao()))
            }
        }
    ),
    onNavigateTo: (route: String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        Text(
            text = "Cart",
            fontWeight = FontWeight.Bold,
            color = Black,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth()
        )

        HorizontalDivider(
            color = Color(0xFFE2E2E2),
            thickness = 1.dp,
            modifier = Modifier.padding( bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = 110.dp
                )
            ) {
                items(uiState.items) { item ->
                    CartRow(
                        item = item,
                        onIncrease = {
                            viewModel.onQuantityChange(item, item.quantity + 1)
                        },
                        onDecrease = {
                            if (item.quantity > 1)
                                viewModel.onQuantityChange(item, item.quantity - 1)
                            else
                                viewModel.removeItem(item)
                        },
                        onRemove = {
                            viewModel.removeItem(item)
                        }
                    )

                    HorizontalDivider(
                        color = Color(0xFFE2E2E2),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }

            Button(
                onClick = { onNavigateTo(Routes.CHECKOUT) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .height(67.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(19.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Go to Checkout",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = String.format("$%.2f", uiState.totalPrice),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(
                                color = Color(0xFF489E67),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CartRow(
    item: CartItemEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Image(
            painter = painterResource(
                id = item.productImage.toIntOrNull() ?: R.drawable.apple
            ),
            contentDescription = item.productName,
            modifier = Modifier
                .size(70.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "1kg",
                fontSize = 14.sp,
                color = MatteGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    IconButton(
                        onClick = onDecrease,
                        modifier = Modifier
                            .size(45.dp)
                            .border(1.dp, Color(0xFFE2E2E2), RoundedCornerShape(17.dp))
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = MatteGray,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = item.quantity.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )

                    IconButton(
                        onClick = onIncrease,
                        modifier = Modifier
                            .size(45.dp)
                            .border(1.dp, Color(0xFFE2E2E2), RoundedCornerShape(17.dp))
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = PrimaryGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Text(
                    text = String.format("$%.2f", item.price),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }
        }
    }
}

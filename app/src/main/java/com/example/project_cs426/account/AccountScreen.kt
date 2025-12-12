package com.example.ecommerce.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_cs426.model.User
import com.example.project_cs426.ui.theme.ProjectCS426Theme

@Composable
fun AccountScreen(
    user: User?,
    onOrdersClick: () -> Unit = {},
    onMyDetailsClick: () -> Unit = {},
    onAddressClick: () -> Unit = {},
    onPaymentMethodsClick: () -> Unit = {},
    onPromoCodeClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user?.image?.toString() ?: "",
                contentDescription = null,
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = user?.name ?: "Guest",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user?.email ?: "",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        AccountMenuItem("Orders", Icons.Default.ShoppingBag, onOrdersClick)
        AccountMenuItem("My Details", Icons.Default.Person, onMyDetailsClick)
        AccountMenuItem("Delivery Address", Icons.Default.LocationOn, onAddressClick)
        AccountMenuItem("Payment Methods", Icons.Default.CreditCard, onPaymentMethodsClick)
        AccountMenuItem("Promo Code", Icons.Default.ConfirmationNumber, onPromoCodeClick)
        AccountMenuItem("About", Icons.Default.Info, onAboutClick)

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE8F5E9))
                .clickable { onLogoutClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Log Out",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
        }
    }
}

@Composable
fun AsyncImage(model: String, contentDescription: String?, modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color.Gray)
    )
}

@Composable
fun AccountMenuItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {

    val fakeUser = com.example.project_cs426.model.FakeData.user

    ProjectCS426Theme {
        AccountScreen(
            user = fakeUser,
            onOrdersClick = {},
            onMyDetailsClick = {},
            onAddressClick = {},
            onPaymentMethodsClick = {},
            onPromoCodeClick = {},
            onAboutClick = {},
            onLogoutClick = {},
        )
    }
}

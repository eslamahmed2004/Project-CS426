package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Checkout(navController: NavHostController, totalPrice: Double) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x47000000)) // scrim rgba(0,0,0,0.28)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                )
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 18.sp,
                    color = Color(0xFF181725)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF181725)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            RowItem(label = "Delivery", value = "Select Method")
            RowItem(label = "Payment", value = "•••• 1234")
            RowItem(label = "Promo Code", value = "Pick discount")

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total Cost", fontSize = 14.sp, color = Color(0xFF181725))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "$${"%.2f".format(totalPrice)}",
                        fontSize = 16.sp,
                        color = Color(0xFF181725)
                    )
                }
                Divider(color = Color(0xFFE6E6E6), thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "By placing an order you agree to our Terms And Conditions",
                fontSize = 11.sp,
                color = Color(0xFF949191),
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B882))
            ) {
                Text(
                    text = "Place Order",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RowItem(label: String, value: String, showChevron: Boolean = true) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF181725)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = value,
                fontSize = 13.sp,
                color = Color(0xFF949191)
            )

            if (showChevron) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF181725)
                )
            }
        }

        Divider(color = Color(0xFFE6E6E6), thickness = 1.dp)
    }
}

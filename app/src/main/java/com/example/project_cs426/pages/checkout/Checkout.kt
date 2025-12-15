package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project_cs426.com.example.project_cs426.navigation.Routes

@Composable
fun Checkout(navController: NavHostController, totalPrice: Double) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0x47000000))) {
        Column(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color.White, shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)).padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text("Checkout", fontSize = 18.sp, color = Color(0xFF181725))
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(16.dp))
            RowItem("Delivery", "Select Method")
            RowItem("Payment", "•••• 1234")
            RowItem("Promo Code", "Pick discount")

            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().height(55.dp)) {
                Text("Total Cost", fontSize = 14.sp)
                Spacer(Modifier.weight(1f))
                Text("$${"%.2f".format(totalPrice)}", fontSize = 16.sp)
            }
            Divider()

            Spacer(Modifier.height(12.dp))
            Text("By placing an order you agree to our Terms And Conditions", fontSize = 11.sp, color = Color(0xFF949191))

            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                navController.navigate(Routes.success) { launchSingleTop = true }
            }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(22.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B882))) {
                Text("Place Order", color = Color.White)
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun RowItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().height(55.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(value, fontSize = 13.sp, color = Color(0xFF949191))
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.ArrowForward, contentDescription = null)
        }
        Divider()
    }
}

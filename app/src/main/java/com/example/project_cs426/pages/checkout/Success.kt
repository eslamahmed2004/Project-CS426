package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.compose.ui.graphics.Color

@Composable
fun Success(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.success_badge), contentDescription = "Success", modifier = Modifier.size(94.dp).padding(top = 40.dp))
            Spacer(Modifier.height(24.dp))
            Text("Your Order Has Been Accepted", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(10.dp))
            Text("Your order has been placed and is on its way to being processed.", style = MaterialTheme.typography.bodyMedium)
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { navController.navigate("track_order") { launchSingleTop = true } }, modifier = Modifier.fillMaxWidth().height(46.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF59B379))) {
                Text("Track Order", color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = {
                navController.popBackStack()
                navController.navigate(Routes.startPage) { launchSingleTop = true }
            }) {
                Text("Back to home")
            }
        }
    }
}

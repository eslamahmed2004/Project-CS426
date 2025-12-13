package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import com.example.project_cs426.com.example.project_cs426.navigation.Routes

@Composable
fun Success(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.success_badge),
                contentDescription = "Order Success",
                modifier = Modifier
                    .size(94.dp)
                    .padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Your Order Has Been Accepted",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 10.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Your order has been placed and is on its way to being processed.",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    // TODO: add TrackOrder screen or flow
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF59B379))
            ) {
                Text("Track Order", color = androidx.compose.ui.graphics.Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(Routes.startPage) {
                    popUpTo(0)
                }
            }) {
                Text("Back to home")
            }
        }
    }
}

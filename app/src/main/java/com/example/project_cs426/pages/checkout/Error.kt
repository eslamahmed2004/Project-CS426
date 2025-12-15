package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import androidx.compose.ui.graphics.Color

@Composable
fun Error(
    navController: NavHostController,
    onRetry: (() -> Unit)? = null
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Order Failed",
                    modifier = Modifier
                        .size(152.dp)
                        .padding(top = 32.dp)
                )

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text = "Oops! Order Failed",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Something went terribly wrong.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        if (onRetry != null) onRetry()
                        else navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC9E5D3))
                ) {
                    Text(
                        text = "Please Try Again",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                TextButton(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Routes.startPage) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text(
                        text = "Back to home",
                        color = Color(0xFF333333),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

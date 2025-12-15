package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import com.example.project_cs426.navigation.Routes

@Composable
fun Error(navController: NavHostController, onRetry: (() -> Unit)? = null) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.error), contentDescription = "Error", modifier = Modifier.size(152.dp).padding(top = 32.dp))
                Spacer(Modifier.height(26.dp))
                Text("Oops! Order Failed", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(10.dp))
                Text("Something went terribly wrong.", style = MaterialTheme.typography.bodyMedium)
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { if (onRetry != null) onRetry() else navController.popBackStack() }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC9E5D3))) {
                    Text("Please Try Again", color = Color.Black)
                }

                Spacer(Modifier.height(14.dp))

                TextButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Routes.START) { launchSingleTop = true }
                }) {
                    Text("Back to home", color = Color(0xFF333333))
                }
            }
        }
    }
}

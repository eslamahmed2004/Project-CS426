package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import com.example.project_cs426.R

@Composable
fun Error(onDismiss: () -> Unit, onRetry: () -> Unit, onBackHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Illustration
        Card(shape = RoundedCornerShape(64.dp), modifier = Modifier.size(120.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.error), // ضع رسم مناسب
                    contentDescription = "Order failed",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Oops! Order Failed", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Something went terribly wrong.", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Please Try Again")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Back to home", modifier = Modifier.padding(top = 8.dp).clickable { onBackHome() })
    }
}

package com.example.project_cs426.pages.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_cs426.model.Product
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.Silver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable

fun ProductBox(
    product: Product,
    onAddToCart: (Product) -> Unit
) {
    var isAdding by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        border = BorderStroke(1.dp, Silver),
        modifier = Modifier
//            .padding(8.dp)
            .width(180.dp)
            .padding(4.dp)



    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = product.name,
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                textAlign = TextAlign.Start,
                color = Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${product.weight}",
                style = MaterialTheme.typography.bodySmall,
                color = MatteGray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "$${product.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    color = Black
                )


                Button(
                    onClick = {
                        isAdding = true
                        onAddToCart(product)

                        scope.launch {
                            delay(2000)
                            isAdding = false
                        }
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAdding) MatteGray else PrimaryGreen
                    ),
                    modifier = Modifier.size(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = if (isAdding) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }


            }
        }


    }
//    Spacer(modifier = Modifier.width(12.dp))
}


package com.example.project_cs426.pages.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.ProjectCS426Theme

/**
 * Simple data model for cart item.
 * If your project already has CartItem or CartItemUi in another file,
 * you can remove this definition and import the existing one instead.
 */
data class CartItem(
    val id: Int,
    val name: String,
    val subtitle: String = "",
    val price: Double,
    val imageRes: Int,
    val quantity: Int = 1
)

@Composable
fun CartItem(
    item: CartItem, // model from your project (id: Int)
    modifier: Modifier = Modifier,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    onItemClick: () -> Unit,
    onOpen: () -> Unit = onItemClick // chevron action (defaults to onItemClick)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min)
            .background(color = androidx.compose.ui.graphics.Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Title + subtitle + quantity controls (clicking column opens details too)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .clickable { onItemClick() }
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quantity controls (minus, qty, plus)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier
                        .size(34.dp)
                        .border(1.dp, MatteGray, CircleShape)
                        .background(androidx.compose.ui.graphics.Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease",
                        tint = DarkGray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, MatteGray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = item.quantity.toString(), color = Black)
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .size(34.dp)
                        .border(1.dp, MatteGray, CircleShape)
                        .background(androidx.compose.ui.graphics.Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = PrimaryGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Price, remove icon, chevron
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            // remove (top)
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = DarkGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // show total price for the row (price * qty)
            val total = item.price * item.quantity
            Text(
                text = "$${String.format("%.2f", total)}",
                style = MaterialTheme.typography.bodyLarge,
                color = Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // chevron (bottom) -> opens product details
            IconButton(onClick = onOpen, modifier = Modifier.size(28.dp)) {
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Open", tint = DarkGray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    ProjectCS426Theme {
        CartItem(
            item = CartItem(
                id = 1,
                name = "Bell Pepper Red",
                subtitle = "1kg, Price",
                price = 4.99,
                imageRes = android.R.drawable.ic_menu_gallery,
                quantity = 2
            ),
            onIncrease = {},
            onDecrease = {},
            onRemove = {},
            onItemClick = {},
            onOpen = {}
        )
    }
}

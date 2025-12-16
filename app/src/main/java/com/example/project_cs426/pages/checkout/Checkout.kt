package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import androidx.compose.ui.text.font.FontWeight

/**
 * CheckoutSheet composable — المحتوى الذي يظهر داخل الـ Modal Bottom Sheet.
 *
 * Parameters:
 *  - total: Double -> إجمالي المبلغ
 *  - onDismiss: () -> Unit -> لإغلاق الشيت
 *  - onPlaceOrder: () -> Unit -> عند الضغط على Place Order
 */
@Composable
fun Checkout(
    total: Double,
    onDismiss: () -> Unit,
    onPlaceOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            // header row: title + close
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Delivery row
            CheckoutRow(
                icon = { Icon(Icons.Default.LocalShipping, contentDescription = "Delivery", tint = DarkGray) },
                title = "Delivery",
                subtitle = "Select Method",
                onClick = { /* open delivery methods */ }
            )

            Divider(thickness = 0.5.dp, color = MatteGray)

            // Payment row
            CheckoutRow(
                icon = { Icon(Icons.Default.CreditCard, contentDescription = "Payment", tint = DarkGray) },
                title = "Payment",
                subtitle = "Select Method",
                onClick = { /* open payment */ }
            )

            Divider(thickness = 0.5.dp, color = MatteGray)

            // Promo Code row
            CheckoutRow(
                icon = { /* optionally show promo icon */ },
                title = "Promo Code",
                subtitle = "Pick discount",
                onClick = { /* open promo */ }
            )

            Divider(thickness = 0.5.dp, color = MatteGray)

            // Total cost row
            CheckoutRow(
                icon = { /* no icon */ },
                title = "Total Cost",
                subtitle = "$${String.format("%.2f", total)}",
                onClick = { /* maybe show details */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "By placing an order you agree to our\nTerms And Conditions",
                style = MaterialTheme.typography.bodySmall,
                color = DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Place Order button
            Button(
                onClick = onPlaceOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text(text = "Place Order", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CheckoutRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                icon()
            }
            Spacer(modifier = Modifier.width(12.dp))
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }

        Icon(
            imageVector = androidx.compose.material.icons.Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = DarkGray
        )
    }
}

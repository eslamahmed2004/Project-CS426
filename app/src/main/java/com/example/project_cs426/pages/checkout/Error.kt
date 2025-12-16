package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_cs426.R
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.Black
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

/**
 * OrderFailedDialog.kt
 *
 * Composable that shows:
 *  - underlying Favourite list (simple mock)
 *  - a centered Dialog (modal) showing "Oops! Order Failed" exactly like the screenshot
 *
 * Usage:
 *  OrderFailedScreen() // shows favourite list with modal open
 *
 * Make sure the drawable ids you pass exist in your resources.
 * For preview we use android.R.drawable.* placeholders so no crash.
 */

@Composable
fun Error(
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    onBackHome: () -> Unit,
    @DrawableRes illustrationRes: Int = R.drawable.error // default; change if you want
) {
    // Use Compose Dialog with custom content
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Close icon top-left (positioned using Box)
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Illustration circle
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEFF8F0),
                    modifier = Modifier
                        .size(140.dp)
                        .shadow(4.dp, CircleShape)
                ) {
                    // Use helper to show image if resource exists, otherwise show icon placeholder
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LocalImageOrPlaceholder(
                            res = illustrationRes,
                            contentDescription = "error illustration",
                            sizePadding = 20.dp,
                            placeholder = Icons.Default.Error
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Oops! Order Failed",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Something went tembly wrong.",
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Primary action
                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text(text = "Please Try Again", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Secondary action - text button
                TextButton(onClick = onBackHome) {
                    Text(text = "Back to home", color = MatteGray)
                }
            }
        }
    }
}

/* ---------- helper composable to safely show an image or a fallback icon ---------- */

@Composable
private fun LocalImageOrPlaceholder(
    @DrawableRes res: Int?,
    contentDescription: String?,
    sizePadding: Dp = 0.dp,
    placeholder: ImageVector = Icons.Default.ShoppingCart
) {
    if (res != null && res != 0) {
        // painterResource is called only when we assume resource id is valid.
        // For preview we supply valid android.R.drawable.* ids, so no crash.
        Image(
            painter = painterResource(id = res),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .padding(sizePadding),
            contentScale = ContentScale.Fit
        )
    } else {
        Icon(
            imageVector = placeholder,
            contentDescription = contentDescription,
            tint = PrimaryGreen,
            modifier = Modifier
                .padding(sizePadding)
                .size(64.dp)
        )
    }
}

/* ---------- Simple Favourite list below the dialog (mock) ---------- */

data class SimpleFavItem(val id: String, val title: String, val subtitle: String, val price: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteListMock(
    items: List<SimpleFavItem>,
    onItemClick: (SimpleFavItem) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favourite", style = MaterialTheme.typography.titleMedium, color = Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavigationBarMock()
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 88.dp, top = 8.dp)
        ) {
            items(items) { it ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Use the safe helper here too
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        LocalImageOrPlaceholder(
                            res = it.imageRes,
                            contentDescription = it.title,
                            sizePadding = 4.dp,
                            placeholder = Icons.Default.ShoppingCart
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = it.title, style = MaterialTheme.typography.bodyLarge, color = Black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it.subtitle, style = MaterialTheme.typography.bodySmall, color = DarkGray)
                    }

                    Text(text = it.price, style = MaterialTheme.typography.bodyMedium, color = Black)
                }

                Divider()
            }
        }
    }
}

@Composable
private fun BottomNavigationBarMock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Storefront, contentDescription = "Shop", tint = DarkGray)
            Text(text = "Shop", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Explore", tint = DarkGray)
            Text(text = "Explore", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart", tint = DarkGray)
            Text(text = "Cart", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourite", tint = PrimaryGreen)
            Text(text = "Favourite", style = MaterialTheme.typography.bodySmall, color = PrimaryGreen)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Account", tint = DarkGray)
            Text(text = "Account", style = MaterialTheme.typography.bodySmall, color = DarkGray)
        }
    }
}

/* ---------------- Previews ---------------- */

@Preview(showBackground = true)
@Composable
fun OrderFailedPreview() {
    // create some fake favourites - use android drawables for preview safety
    val items = listOf(
        SimpleFavItem("1", "Sprite Can", "325ml, Price", "$1.50", android.R.drawable.ic_dialog_map),
        SimpleFavItem("2", "Diet Coke", "355ml, Price", "$1.99", android.R.drawable.ic_dialog_map),
        SimpleFavItem("3", "Apple & Grape Juice", "2L, Price", "$15.50", android.R.drawable.ic_dialog_map),
        SimpleFavItem("4", "Coca Cola Can", "325ml, Price", "$4.99", android.R.drawable.ic_dialog_map),
        SimpleFavItem("5", "Pepsi Can", "330ml, Price", "$4.99", android.R.drawable.ic_dialog_map),
    )

    var visible by remember { mutableStateOf(true) }

    // full screen preview that shows favourite list + dialog on top
    Box(modifier = Modifier.fillMaxSize()) {
        FavouriteListMock(items = items)
        if (visible) {
            Error(
                onDismiss = { visible = false },
                onRetry = { /* retry logic: for preview just close dialog */ visible = false },
                onBackHome = { /* navigate to home: for preview just close */ visible = false },
                illustrationRes = android.R.drawable.ic_dialog_alert // safe preview illustration
            )
        }
    }
}

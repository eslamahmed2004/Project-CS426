package com.example.project_cs426.pages.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.DarkGray

@Composable
fun FavouriteItem(
    item: FavouriteItemUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
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
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format("%.2f", item.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Open",
                    tint = DarkGray
                )
            }
        }

        Divider()
    }
}

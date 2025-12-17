package com.example.project_cs426.pages.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_cs426.model.CategoryProducts
import com.example.project_cs426.model.FakeData.categories
import com.example.project_cs426.ui.theme.Black

@Composable
fun Explore(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Find Products",
                fontWeight = FontWeight.Bold,
                color = Black,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                placeHolder = "Search Store",
                onSearch = { query ->
                    navController.navigate("productsBySearch/${query}")
                }
            )
            categories.chunked(2).forEach { rowItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryBox(rowItem[0]) { selectedCategory ->
                            navController.navigate("productsByCategory/${selectedCategory.category}")
                        }
                    }

                    if (rowItem.size > 1) {
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryBox(rowItem[1]) { selectedCategory ->
                                navController.navigate("productsByCategory/${selectedCategory.category}")
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CategoryBox(cat: CategoryProducts, onClick: (CategoryProducts) -> Unit) {
    val lighterColor = lerp(Color(cat.color), Color.White, 0.2f)
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(cat.color).copy(alpha = 0.1f)),
        border = BorderStroke(2.dp, lerp(Color(cat.color), Color.White, 0.7f)),
        modifier = Modifier
            .height(180.dp)
            .padding(2.dp)
            .clickable { onClick(cat) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(cat.image),
                contentDescription = cat.category,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = cat.category,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

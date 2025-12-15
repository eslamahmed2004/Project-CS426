package com.example.project_cs426.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project_cs426.R
import com.example.project_cs426.model.FakeData.categories
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.pages.product.ProductBox
import com.example.project_cs426.pages.product.SearchBar
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.PrimaryGreen


@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        item {
            Header()
        }

        item {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                placeHolder = "Search products",
                onSearch = { query ->
                    navController.navigate("productsBySearch/$query")
                }
            )
        }

        item {
            BannerCarousel()
        }

        item {
            ShowSliderProducts(navController)
        }

        item {
            ShowSliderCategory(navController)
        }
    }
}
@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.carrot_rgb),
            contentDescription = "Carrot",
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.location),
                contentDescription = "location",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Dhaka, Banassre",
                color = DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BannerCarousel() {
    val images = listOf(
        R.drawable.banner,
        R.drawable.banner,
        R.drawable.banner
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(images) { index, imgRes ->
            Image(
                painter = painterResource(imgRes),
                contentDescription = "banner $index",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillParentMaxWidth(0.95f)
                    .fillMaxHeight()
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}



@Composable
fun ShowSliderProducts(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {

        categories.take(2).forEach { categoryData ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = categoryData.category,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )

                Text(
                    text = "See all",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = PrimaryGreen,
                    modifier = Modifier.clickable {
                        navController.navigate("productsByCategory/${categoryData.category}")
                    }
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categoryData.products.take(10)) { product ->
                    ProductBox(product)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShowSliderCategory(navController: NavController) {
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Groceries",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = Black
            )

            Text(
                text = "See all",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryGreen,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.EXPLORE)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories.take(4)) { cat ->
                Card(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .height(90.dp)
                        .width(200.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(cat.color)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate("productsByCategory/${cat.category}")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(cat.image),
                            contentDescription = cat.category,
                            modifier = Modifier.size(90.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = cat.category,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

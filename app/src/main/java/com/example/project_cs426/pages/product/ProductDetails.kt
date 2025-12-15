package com.example.project_cs426.pages.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_cs426.viewmodel.ProductViewModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.ui.theme.ProjectCS426Theme
import com.example.project_cs426.model.FakeData
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val product = viewModel.product
    val quantity = viewModel.quantity
    val isFavorite = viewModel.isFavorite

    if (product == null) {
        Text("Product not found")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box {
            Image(
                painter = painterResource(product.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, null)
            }

            IconButton(
                onClick = { /* Share */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Share, null)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(product.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(product.weight, color = Color.Gray)
            }

            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFF53B175)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.decrease() }) {
                Icon(Icons.Default.Remove, null)
            }

            Text("$quantity", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 12.dp))

            IconButton(onClick = { viewModel.increase() }) {
                Icon(Icons.Default.Add, null)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "\$${product.price}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Product Detail", fontWeight = FontWeight.Bold)
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    null
                )
            }

            if (expanded) {
                Text(
                    text = product.description,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { /* Add To Cart */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53B175))
        ) {
            Text("Add To Basket", fontSize = 18.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {

    val fakeViewModel = object : ProductViewModel() {
        init {

            product = com.example.project_cs426.model.FakeData.products.first()
            quantity = 1
            isFavorite = false
        }
    }

    val navController = rememberNavController()

    ProjectCS426Theme {
        ProductDetailsScreen(
            navController = navController,
            viewModel = fakeViewModel
        )
    }
}


package com.example.project_cs426.pages.product

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.project_cs426.R
import com.example.project_cs426.data.local.AppDatabase
import com.example.project_cs426.model.FakeData.categories
import com.example.project_cs426.pages.product.ProductBox
import com.example.project_cs426.repository.CartRepository
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.viewmodel.HomeViewModel

@Composable
fun ProductsByCategoryScreen(
    categoryName: String,
    navController: NavController,
    viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as Application)
                val db = AppDatabase.getInstance(app)
                HomeViewModel(CartRepository(db.cartDao()))
            }
        }
    )) {

    val category = categories.firstOrNull { it.category == categoryName }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                textAlign = TextAlign.Center,
                color = Black
            )
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = Color.Black,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {

                    }
            )
        }

        LazyColumn(
            modifier = Modifier.padding(3.dp)
        ) {

            if (category != null) {

                items(category.products.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProductBox(
                            rowItems[0],
                            onAddToCart = { viewModel.addToCart(it) }
                        )

                        if (rowItems.size > 1) {
                            ProductBox(
                                rowItems[1],
                                onAddToCart = { viewModel.addToCart(it) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }

            } else {
                item {
                    Text(
                        text = "No products found",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
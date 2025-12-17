package com.example.project_cs426.pages.product

import android.app.Application
import com.example.project_cs426.pages.product.ProductBox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.project_cs426.R
import com.example.project_cs426.data.local.AppDatabase
import com.example.project_cs426.ui.theme.MatteGray
import kotlin.collections.chunked
import com.example.project_cs426.model.FakeData.products



@Composable
fun ProductsBySearchScreen(
    initQuery: String = "",
    navController: NavController,
    viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as Application)
                val db = AppDatabase.getInstance(app)
                HomeViewModel(CartRepository(db.cartDao()))
            }
        }
    )){
    var query by remember { mutableStateOf(initQuery) }
    val filteredProducts = products.filter { it.name.contains(query, ignoreCase = true) }
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        item{
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)

            ) {
                SearchBar(
                    query= query,
                    modifier = Modifier.weight(1f),
                    onSearch = { newQuery ->
                        query = newQuery
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "Filter Icon",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {

                        }
                )
            }

            if (filteredProducts.isEmpty()) {
                Text(
                    text = "No products for your searching",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MatteGray
                )
            } else {
                Text(
                    text = "Founded ${filteredProducts.size} products",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MatteGray
                )
                filteredProducts.chunked(2).forEach { rowItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProductBox(
                            rowItem[0],
                            onAddToCart = { viewModel.addToCart(it) }

                        )
                        if (rowItem.size > 1) {
                            ProductBox(
                                rowItem[1],
                                onAddToCart = { viewModel.addToCart(it) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

        }
    }

}
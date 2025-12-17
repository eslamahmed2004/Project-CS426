package com.example.project_cs426.pages.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project_cs426.data.local.AppDatabase
import com.example.project_cs426.model.Product
import com.example.project_cs426.model.ProductWithCategory
import com.example.project_cs426.pages.admin.dialog.AddCategoryDialog
import com.example.project_cs426.pages.admin.dialog.AddProductDialog
import com.example.project_cs426.pages.admin.dialog.EditProductDialog
import com.example.project_cs426.viewmodel.AdminViewModel


@Composable
fun AdminDashboard(
    productCount: Int,
    userCount: Int,
    products: List<ProductWithCategory>,
    onAddCategory: () -> Unit,
    onAddProduct: () -> Unit,
    onDeleteProduct: (Int) -> Unit,
    onUpdateProduct: (Int) -> Unit
) {

    var showCategoryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Admin Dashboard",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AdminCard("Products", productCount.toString())
            AdminCard("Users", userCount.toString())
        }


        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick =onAddCategory,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddProduct,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53B175))
        ) {
            Text("Add Product", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(products) { product ->
                AdminProductItem(
                    product = product,
                    onDelete = { onDeleteProduct(product.id) },
                    onEdit = { onUpdateProduct(product.id) }
                )
            }
        }
    }
}

@Composable
fun AdminDashboardRoute(
    navController: NavHostController
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }

    val viewModel: AdminViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AdminViewModel(
                    productDao = db.productDao(),
                    userDao = db.userDao(),
                    categoryDao = db.categoryDao()
                ) as T
            }
        }
    )
    val productCount = viewModel.productCount.collectAsState()
    val userCount = viewModel.userCount.collectAsState()
    val products = viewModel.productsWithCategory.collectAsState()
    val categories by viewModel.categories.collectAsState()



    var showDialog by remember { mutableStateOf(false) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }
    var showCategoryDialog by remember { mutableStateOf(false) }




    if (showCategoryDialog) {
        AddCategoryDialog(
            onDismiss = { showCategoryDialog = false },
            onAdd = { name ->
                viewModel.addCategory(name)
                showCategoryDialog = false
            }
        )
    }



    // ➕ Add Dialog

    if (showDialog) {
        AddProductDialog(
            categories = categories,
            onDismiss = { showDialog = false },
            onAdd = { name, price, categoryId ->
                viewModel.addProduct(
                    name = name,
                    price = price,
                    categoryId = categoryId
                )
                showDialog = false
            }
        )

    }


    // ✏ Edit Dialog
    editingProduct?.let { product ->
        EditProductDialog(
            product = product,
            onDismiss = { editingProduct = null },
            onConfirm = {
                viewModel.updateProduct(it)
                editingProduct = null
            }
        )
    }

    // 🧠 UI
    AdminDashboard(
        productCount = productCount.value,
        userCount = userCount.value,
        products = products.value,
        onAddCategory = { showCategoryDialog = true },
        onAddProduct = { showDialog = true },
        onDeleteProduct = { productId ->
            viewModel.deleteProductById(productId)
        },
        onUpdateProduct = { productId ->
            // لاحقًا نجيب المنتج ونعمله Edit
        }
    )


}


//
//@Preview(showBackground = true)
//@Composable
//fun AdminPreview() {
//    AdminDashboard()
//}


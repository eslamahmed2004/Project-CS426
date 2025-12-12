package com.example.project_cs426


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.project_cs426.ui.theme.ProjectCS426Theme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(onClose: () -> Unit = {}, onApply: (String, String) -> Unit = { _, _ -> }) {

    val categories = listOf("Eggs", "Noodles & Pasta", "Chips & Crisps", "Fast Food")

    val brandsByCategory = mapOf(
        "Eggs" to listOf("Kazi Farmas", "Individual Collection"),
        "Noodles & Pasta" to listOf("Ifad", "Cocola"),
        "Chips & Crisps" to listOf("Pringles", "Ifad"),
        "Fast Food" to listOf("KFC", "Mac", "Cook Door")
    )

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    var selectedBrand by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filters", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        onApply(selectedCategory ?: "", selectedBrand ?: "")
                    },
                    enabled = selectedCategory != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Apply Filter", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            categories.forEach { cat ->
                FilterItem(
                    text = cat,
                    checked = selectedCategory == cat
                ) {
                    selectedCategory = if (it) cat else null
                    selectedBrand = null
                }
            }

            Spacer(Modifier.height(25.dp))

            Text("Brand", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            if (selectedCategory == null) {
                Spacer(Modifier.height(10.dp))
                Text("Select a category first.", color = Color.Gray)
            } else {
                Spacer(Modifier.height(10.dp))
                val brands = brandsByCategory[selectedCategory] ?: emptyList()

                brands.forEach { brand ->
                    FilterItem(
                        text = brand,
                        checked = selectedBrand == brand
                    ) {
                        selectedBrand = if (it) brand else null
                    }
                }
            }
        }
    }
}

@Composable
fun FilterItem(text: String, checked: Boolean, onCheck: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheck,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4CAF50)
            )
        )

        Text(
            text,
            fontSize = 17.sp,
            color = if (checked) Color(0xFF4CAF50) else Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FiltersScreenPreview() {
    ProjectCS426Theme {
        FiltersScreen(
            onClose = {},
            onApply = { _, _ -> }
        )
    }
}




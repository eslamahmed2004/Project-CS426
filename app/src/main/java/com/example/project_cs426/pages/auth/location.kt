package com.example.project_cs426.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.R
import com.example.project_cs426.data.local.LocationPreferences
import com.example.project_cs426.com.example.project_cs426.navigation.Routes
import com.example.project_cs426.viewmodel.LocationViewModel
import kotlinx.coroutines.launch


@Composable
fun location(
    navController: NavHostController,
    viewModel: LocationViewModel = viewModel()
) {

    val bg = Color(0xFFF7F7F8)
    val scrollState = rememberScrollState()
    var locationError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val locationPrefs = remember { LocationPreferences(context) }



    // API DATA
    val countries by viewModel.countries.collectAsState(initial = emptyList())


    // Load API once
    LaunchedEffect(Unit) {
        viewModel.loadCountries()
    }

    // States
    var selectedCountry by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }


    val countryNames = countries.map { it.country }
    val cities = countries
        .firstOrNull { it.country == selectedCountry }
        ?.cities ?: emptyList()

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(75.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.illustration),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(0.8f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Select Your Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Switch on your location to stay in tune with what's happening in your area",
                fontSize = 13.sp,
                color = Color(0xFF7A7A7A),
                modifier = Modifier.padding(horizontal = 12.dp),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Country
            SimpleDropdown(
                label = "Country",
                value = if (selectedCountry.isEmpty()) "Select Country" else selectedCountry,
                options = countryNames,
                onSelect = {
                    selectedCountry = it
                    selectedCity = ""
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            //  City
            SimpleDropdown(
                label = "City",
                value = if (selectedCity.isEmpty()) "Select City" else selectedCity,
                options = cities,
                onSelect = { selectedCity = it }
            )

            Spacer(modifier = Modifier.height(30.dp))
            val scope = rememberCoroutineScope()

            Button(
                onClick = {
                    if (selectedCountry.isEmpty() || selectedCity.isEmpty()) {
                        locationError = true
                    } else {

                        locationError = false

                        scope.launch {
                            locationPrefs.saveLocation(selectedCountry, selectedCity)
                        }
                        navController.navigate(Routes.login)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF53B175)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Submit", color = Color.White, fontSize = 16.sp)
            }
            if (locationError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please select both country and city",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun SimpleDropdown(
    label: String,
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 12.sp, color = Color(0xFF8A8A8A))
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(0.5.dp, Color(0xFFDDDDDD))
                .clickable { expanded = true }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    location(navController = rememberNavController())
}

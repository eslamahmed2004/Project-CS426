package com.example.project_cs426.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun register(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()

) {
    val bg = Color.White
    val green = Color(0xFF53B175)
    val scrollState = rememberScrollState()


    Surface(modifier = modifier.fillMaxSize(), color = bg) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .size(72.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.orangecarrot),
                    contentDescription = "Carrot logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Signup",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your credentials to continue",
                fontSize = 14.sp,
                color = Color(0xFF888888)
            )
            Spacer(modifier = Modifier.height(18.dp))

            val usernameError = viewModel.usernameError.value
// username
            OutlinedTextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.username.value = it
                    viewModel.usernameError.value = null
                                },
                label = { Text("Username") },
                isError = usernameError != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    focusedPlaceholderColor = Color(0xFFAAAAAA),
                    unfocusedPlaceholderColor = Color(0xFFAAAAAA),

                    cursorColor = Color(0xFF53B175)
                )
            )
            
            Spacer(modifier = Modifier.height(18.dp))
  // Email
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it
                    viewModel.errorMessage.value = null

                },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor= Color(0xFFAAAAAA),
                    unfocusedPlaceholderColor = Color(0xFFAAAAAA),
                    cursorColor = Color(0xFF53B175),

                )
            )

            Spacer(modifier = Modifier.height(12.dp))

   // password
            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it
                    viewModel.errorMessage.value = null
                                },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            imageVector = if (viewModel.passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (viewModel.passwordVisible.value) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,         // لما يكتب
                    unfocusedTextColor = Color.Black,       // لون الكتابة
                    disabledTextColor = Color.Black,
                    cursorColor = Color(0xFF53B175),        // لون المؤشر

                    focusedPlaceholderColor = Color(0xFFAAAAAA),
                    unfocusedPlaceholderColor = Color(0xFFAAAAAA)
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "By continuning you agree to your  ", color = Color(0xFF666666),)
                Text(
                    text = "Terms of Service",
                    color = green,
                    modifier = Modifier.clickable {
                        navController?.navigate(Routes.TERMS)
                    }
                )

            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "and ", color = Color(0xFF666666),)
                Text(
                    text = "Privacy Policy",
                    color = green,
                    modifier = Modifier.clickable {
                        navController?.navigate(Routes.PRIVACY)
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            val globalError = viewModel.errorMessage.value

            Button(
                onClick = {
                    viewModel.signup(
                        onSuccess = {
                            navController?.navigate(Routes.LOGIN)
                        },
                        onError = { msg ->
                            viewModel.errorMessage.value = msg
                        }
                    )                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = green),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign Up", color = Color.White, fontSize = 16.sp)
            }
            if (!globalError.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = globalError,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account ? ", color = Color(0xFF666666),)
                Text(
                    text = "Login",
                    color = green,
                    modifier = Modifier.clickable {
                        navController?.navigate(Routes.LOGIN)
                    }
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupPreview() {
//    val navController = rememberNavController()
//    signup(navController = navController, viewModel = AuthViewModel())
//}


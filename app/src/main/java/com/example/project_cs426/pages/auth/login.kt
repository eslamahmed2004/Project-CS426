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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project_cs426.R
import com.example.project_cs426.navigation.Routes
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun login(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val bg = Color.White
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
                text = "Login",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your email and password",
                fontSize = 14.sp,
                color = Color(0xFF888888)
            )

            Spacer(modifier = Modifier.height(18.dp))
   // Email
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it
                    viewModel.errorMessage.value = null

                },
                placeholder = { Text("Email", color = Color(0xFF999999)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    focusedPlaceholderColor = Color(0xFFAAAAAA),
                    unfocusedPlaceholderColor = Color(0xFFAAAAAA),

                    cursorColor = PrimaryGreen
                ),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* focus to next field */ }
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
// Password
            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it
                    viewModel.errorMessage.value = null

                },
                placeholder = { Text("Password", color = Color(0xFF999999)) },
                singleLine = true,
                visualTransformation = if (viewModel.passwordVisible.value)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
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
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    focusedPlaceholderColor = Color(0xFFAAAAAA),
                    unfocusedPlaceholderColor = Color(0xFFAAAAAA),

                    cursorColor = PrimaryGreen
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { /* TODO: navigate to forgot password */ }) {
                    Text(text = "Forgot Password?", color = Color(0xFF444444))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            val globalError = viewModel.errorMessage.value


            Button(
                onClick = {
                    viewModel.login(
                        onSuccess = { navController.navigate(Routes.HOME) },
                        onError = { msg ->
                            viewModel.errorMessage.value = msg
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(PrimaryGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Log In", color = Color.White, fontSize = 16.sp)
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
                Text(text = "Don't have an account? ", color = Color(0xFF666666),)
                Text(
                    text = "Signup",
                    color = PrimaryGreen,
                    modifier = Modifier.clickable {
                        navController?.navigate(Routes.REGISTER)
                    }
                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    val navController = rememberNavController()
//    login(navController = navController ,viewModel = AuthViewModel())
//}

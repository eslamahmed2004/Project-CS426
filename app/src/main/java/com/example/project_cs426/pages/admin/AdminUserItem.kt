package com.example.project_cs426.pages.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_cs426.data.local.entity.UserEntity

@Composable
fun AdminUserItem(user: UserEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = user.name, fontWeight = FontWeight.Bold)
        Text(text = user.email, fontSize = 13.sp, color = Color.Gray)
        Text(
            text = user.role,
            fontSize = 12.sp,
            color = if (user.role == "ADMIN") Color.Red else Color.DarkGray
        )
    }
}

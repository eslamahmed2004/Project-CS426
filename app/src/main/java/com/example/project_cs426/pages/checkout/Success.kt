package com.example.project_cs426.pages.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project_cs426.ui.theme.DarkGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.project_cs426.ui.theme.ProjectCS426Theme // غيّر اسم الثيم لو مختلف عندك

/**
 * OrderSuccessScreen
 *
 * @param navController optional NavController — إذا مررتّه سيتم استخدامه للعودة للصفحة الرئيسية
 * @param onTrackOrder callback عند الضغط على Track Order
 * @param onBackHome callback عند الضغط على Back to home (افتراضيًا navigate للـ home إذا مررت navController)
 */
@Composable
fun Success(
    navController: NavController? = null,
    onTrackOrder: () -> Unit = {},
    onBackHome: () -> Unit = {
        // Default behaviour: navigate to home if navController موجود
        navController?.navigate("home") {
            popUpTo(0) // or popUpTo(Routes.HOME) حسب إعداداتك
        }
    }
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(44.dp))

            // decorative top area with soft background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                // subtle vertical gradient background (soft)
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x00FFFFFF),
                                    Color(0x10E6F6EC) // very light green tint
                                )
                            )
                        )
                )

                // small decorative circles (confetti)
                val small = 12.dp
                Box(modifier = Modifier
                    .offset(x = (-90).dp, y = (-40).dp))
                { Box(modifier = Modifier.size(small).background(Color(0xFF55C57A), shape = CircleShape)) }

                Box(modifier = Modifier
                    .offset(x = 80.dp, y = (-60).dp))
                { Box(modifier = Modifier.size(8.dp).background(Color(0xFFFF6B6B), shape = CircleShape)) }

                Box(modifier = Modifier
                    .offset(x = 30.dp, y = 40.dp))
                { Box(modifier = Modifier.size(10.dp).background(Color(0xFF6EC6FF), shape = CircleShape)) }

                // main green circle with check
                val circleSize = 140.dp
                Surface(
                    modifier = Modifier
                        .size(circleSize)
                        .shadow(elevation = 8.dp, shape = CircleShape),
                    color = PrimaryGreen,
                    shape = CircleShape
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            tint = Color.White,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Your Order has been\naccepted",
                color = Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your items has been placed and is on\nit's way to being processed",
                color = DarkGray,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Track Order button
            Button(
                onClick = onTrackOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text(text = "Track Order", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back to home (text button)
            TextButton(
                onClick = onBackHome
            ) {
                Text(
                    text = "Back to home",
                    color = MatteGray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/* ---------------- Preview ---------------- */

@Preview(showBackground = true)
@Composable
fun SuccessPreview() {
    ProjectCS426Theme {
        Success(
            navController = rememberNavControllerForPreview()
        )
    }
}

/**
 * Helper for preview to avoid depending on actual NavController in preview.
 * This returns a simple NavController when previewing to prevent null issues.
 */
@Composable
private fun rememberNavControllerForPreview(): NavController {
    // Since Preview can't create full NavController easily, we return a lightweight one.
    // This function just creates and returns a NavController only when running the preview.
    return rememberNavController()
}

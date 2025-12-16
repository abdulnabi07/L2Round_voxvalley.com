package com.l2round_voxvalleycom.presentation.onboarding

import com.l2round_voxvalleycom.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l2round_voxvalleycom.ui.components.AppButton
import com.l2round_voxvalleycom.ui.components.SetStatusBar
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary
import com.l2round_voxvalleycom.ui.theme.TextSecondary




@Composable
fun OnboardingScreen(
    navController: NavController
) {
    SetStatusBar(
        darkIcons = true,
        backgroundColor = PrimaryGreen
    )
    val pages = listOf(
        OnboardingPage(
            image = R.drawable.frameone,
            title = "Get a New Number Instantly",
            description = "Choose from multiple countries and start calling in minutes."
        ),
        OnboardingPage(
            image = R.drawable.frametwo,
            title = "Affordable Plans, No Hidden Fees",
            description = "Transparent pricing with flexible plans for your needs."
        ),
        OnboardingPage(
            image = R.drawable.framethree,
            title = "Secure & Reliable Calling",
            description = "High-quality calls with complete privacy and security."
        )
    )

    var currentPage by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        OnboardingTopBar(
            currentPage = currentPage,
            totalPages = pages.size,
            onSkip = {
                navController.navigate("login") {
                    popUpTo("onboarding") { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))


        Image(
            painter = painterResource(pages[currentPage].image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .height(320.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {

            Text(
                text = pages[currentPage].title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pages[currentPage].description,
                fontSize = 14.sp,
                color = TextSecondary
            )
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = {
                if (currentPage == pages.lastIndex) {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                } else {
                    currentPage++
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text(
                text = if (currentPage == pages.lastIndex) "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}


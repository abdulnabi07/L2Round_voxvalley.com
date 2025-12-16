package com.l2round_voxvalleycom.presentation.splash

import com.l2round_voxvalleycom.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay




@Composable
fun LogoSplashScreen(nav: NavController) {

    LaunchedEffect(Unit) {
        delay(2000)
        nav.navigate("onboarding") {
            popUpTo("logo") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier.size(140.dp)
        )
    }
}

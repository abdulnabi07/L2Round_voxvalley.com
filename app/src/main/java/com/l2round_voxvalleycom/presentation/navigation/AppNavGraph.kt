package com.l2round_voxvalleycom.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.l2round_voxvalleycom.presentation.auth.LoginScreen
import com.l2round_voxvalleycom.presentation.auth.OTPScreen
import com.l2round_voxvalleycom.presentation.auth.SignUpScreen
import com.l2round_voxvalleycom.presentation.dashboard.DashboardScreen

import com.l2round_voxvalleycom.presentation.onboarding.OnboardingScreen
import com.l2round_voxvalleycom.presentation.splash.LogoSplashScreen

@Composable
fun AppNavGraph(nav: NavHostController) {

    NavHost(nav, startDestination = "logo") {

        composable("logo") { LogoSplashScreen(nav) }
        composable("onboarding") { OnboardingScreen(nav) }
        composable("login") { LoginScreen(nav) }
        composable("signup") { SignUpScreen(nav) }
        composable("otp") { OTPScreen(nav) }
        composable("dashboard") { DashboardScreen(nav) }
    }
}

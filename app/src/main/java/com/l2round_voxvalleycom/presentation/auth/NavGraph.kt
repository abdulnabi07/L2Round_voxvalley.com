package com.l2round_voxvalleycom.presentation.auth

sealed class Screen(val route: String) {
    object LogoSplash : Screen("logo_splash")
    object Splash1 : Screen("splash1")
    object Splash2 : Screen("splash2")
    object Splash3 : Screen("splash3")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object OTP : Screen("otp")
}

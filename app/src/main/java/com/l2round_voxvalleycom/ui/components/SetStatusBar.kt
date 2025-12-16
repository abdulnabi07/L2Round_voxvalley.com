package com.l2round_voxvalleycom.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetStatusBar(
    darkIcons: Boolean,
    backgroundColor: Color = Color.Transparent
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = darkIcons
        )
    }
}

package com.l2round_voxvalleycom.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    background: Color = PrimaryGreen
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(background)
    ) {
        Text(text, color = Color.White, fontSize = 16.sp)
    }
}

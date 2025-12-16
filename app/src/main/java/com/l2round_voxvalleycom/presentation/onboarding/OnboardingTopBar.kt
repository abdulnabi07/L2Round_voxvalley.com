package com.l2round_voxvalleycom.presentation.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.l2round_voxvalleycom.ui.theme.DividerColor
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary

@Composable
fun OnboardingTopBar(
    currentPage: Int,
    totalPages: Int,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        LinearProgressIndicator(
            progress = (currentPage + 1) / totalPages.toFloat(),
            modifier = Modifier
                .weight(1f)
                .height(6.dp),
            color = PrimaryGreen,
            trackColor = DividerColor
        )


        if (currentPage < totalPages - 1) {
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Skip",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSkip() }
            )
        }

        Spacer(modifier = Modifier.width(12.dp))


    }
}

package com.l2round_voxvalleycom.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary
import com.l2round_voxvalleycom.ui.theme.TextSecondary

@Composable
fun DashboardScreen(    navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {

        // Top header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen)
                .padding(20.dp)
        ) {
            Text(
                text = "Dashboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Simple cards row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SimpleCard(title = "Balance", value = "₹1,200")
            SimpleCard(title = "Active Number", value = "1")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Recent Activity",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        SimpleActivityItem("Outgoing Call", "India → USA", "- ₹12")
        SimpleActivityItem("Incoming Call", "USA → India", "+ ₹8")
    }
}

@Composable
private fun SimpleCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .height(90.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 13.sp, color = TextSecondary)
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun SimpleActivityItem(
    title: String,
    subtitle: String,
    amount: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(text = subtitle, fontSize = 12.sp, color = TextSecondary)
        }
        Text(
            text = amount,
            fontWeight = FontWeight.SemiBold,
            color = if (amount.startsWith("-")) Color.Red else PrimaryGreen
        )
    }
}

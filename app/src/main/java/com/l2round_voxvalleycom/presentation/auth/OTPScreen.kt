package com.l2round_voxvalleycom.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.l2round_voxvalleycom.R
import com.l2round_voxvalleycom.ui.components.SetStatusBar

import com.l2round_voxvalleycom.ui.theme.DividerColor
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary
import com.l2round_voxvalleycom.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun OTPScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()

) {


    SetStatusBar(
        darkIcons = true,
        backgroundColor = PrimaryGreen
    )
    var otp by remember { mutableStateOf("") }
    var timer by remember { mutableStateOf(60) }


    val context = LocalContext.current
    val activity = context as Activity



    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(R.drawable.back), modifier = Modifier.size(22.dp),
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Verify Your Number",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Enter the OTP sent to +91 *****099 ",
                fontSize = 14.sp,
                color = TextSecondary
            )
            Text(
                text = "Edit",
                fontSize = 14.sp,
                color = PrimaryGreen,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // OTP circles
        OTPInput(
            otp = otp,
            onOtpChange = { if (it.length <= 6) otp = it }
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {

                Log.d("OTP_VERIFY", "Verify button clicked")
                Log.d("OTP_VERIFY", "Entered OTP: $otp")

                if (otp.length < 6) {
                    Log.e("OTP_VERIFY", "OTP length is invalid: ${otp.length}")
                    return@Button
                }

                Log.d("OTP_VERIFY", "Calling verifyOtp()")

                viewModel.verifyOtp(otp) {

                    Log.d("OTP_VERIFY", "OTP verification SUCCESS")
                    Log.d("OTP_VERIFY", "Navigating to Dashboard")

                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            enabled = !viewModel.loading
        ) {
            Text(
                text = if (viewModel.loading) "Verifying..." else "Verify",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }


        Spacer(modifier = Modifier.height(24.dp))


        if (timer > 0) {
            Text(
                text = "Resend OTP In ${timer}s",
                fontSize = 14.sp,
                color = TextSecondary
            )
        } else {
            Text(
                text = "Resend OTP",
                fontSize = 14.sp,
                color = PrimaryGreen,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    timer = 60

                }
            )
        }
    }
}


@Composable
private fun OTPInput(
    otp: String,
    onOtpChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }


    BasicTextField(
        value = otp,
        onValueChange = {
            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                onOtpChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .focusRequester(focusRequester)
            .size(0.8.dp)
    )

    // OTP Boxes
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { focusRequester.requestFocus() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(4) { index ->
            OTPChar(
                char = otp.getOrNull(index)?.toString() ?: ""
            )
        }
    }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@Composable
private fun OTPChar(
    char: String
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .border(
                width = 1.dp,
                color = DividerColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}

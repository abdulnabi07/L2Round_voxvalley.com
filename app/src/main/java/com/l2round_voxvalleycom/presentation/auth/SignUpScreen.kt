package com.l2round_voxvalleycom.presentation.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.l2round_voxvalleycom.R
import com.l2round_voxvalleycom.ui.components.SetStatusBar
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary


@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    SetStatusBar(
        darkIcons = true,
        backgroundColor = PrimaryGreen
    )

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }


    val context = LocalContext.current
    val activity = context as Activity

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(
                    context.getString(R.string.default_web_client_id)
                )
                .requestEmail()
                .build()
        )
    }



    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d("GOOGLE_AUTH", "Result code: ${result.resultCode}")

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("GOOGLE_AUTH", "Account email: ${account.email}")

                    val idToken = account.idToken
                    Log.d("GOOGLE_AUTH", "ID Token: $idToken")

                    if (idToken == null) {
                        Log.e("GOOGLE_AUTH", "ID Token is NULL")
                        return@rememberLauncherForActivityResult
                    }

                    viewModel.signInWithGoogle(idToken) {
                        Log.d("GOOGLE_AUTH", "Firebase login success")
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }

                } catch (e: ApiException) {
                    Log.e("GOOGLE_AUTH", "Google sign-in failed", e)
                }
            }
        }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(R.drawable.back), modifier = Modifier.size(22.dp),
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Create Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Name
        Text(
            text = "Name",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Phone Number
        Text(
            text = "Phone Number",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text("Phone Number") },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.india),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {

                Log.d("OTP_FLOW", "Continue button clicked")

                // Basic validation
                if (name.isBlank()) {
                    Log.e("OTP_FLOW", "Name is empty")
                    return@Button
                }

                if (phone.length < 10) {
                    Log.e("OTP_FLOW", "Invalid phone number: $phone")
                    return@Button
                }

                val fullPhoneNumber = "+91$phone"
                Log.d("OTP_FLOW", "Sending OTP to $fullPhoneNumber")

                viewModel.sendOtp(
                    phoneNumber = fullPhoneNumber,
                    activity = activity
                ) {
                    Log.d("OTP_FLOW", "OTP sent successfully, navigating to OTP screen")

                    navController.navigate("otp")
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
                text = if (viewModel.loading) "Sending OTP..." else "Continue",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }



        Spacer(modifier = Modifier.height(28.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(20.dp))

        SocialButton(
            text = "Continue with Google",
            icon = R.drawable.google,
            onClick = {

                launcher.launch(googleSignInClient.signInIntent)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialButton(
            text = "Continue with Apple",
            icon = R.drawable.apple,
            onClick = { /* later */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Already Have An Account? ")
            Text(
                text = "Log In",
                color = PrimaryGreen,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}

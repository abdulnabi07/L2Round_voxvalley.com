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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.l2round_voxvalleycom.R
import com.l2round_voxvalleycom.ui.components.AppButton
import com.l2round_voxvalleycom.ui.components.SetStatusBar
import com.l2round_voxvalleycom.ui.theme.DividerColor
import com.l2round_voxvalleycom.ui.theme.PrimaryGreen
import com.l2round_voxvalleycom.ui.theme.TextPrimary
import com.l2round_voxvalleycom.ui.theme.TextSecondary






@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    SetStatusBar(
        darkIcons = false,
        backgroundColor = PrimaryGreen
    )

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


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryGreen)
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {

                Text(
                    text = "Welcome Back!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Log in to stay connected anytime, anywhere.",
                    fontSize = 14.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Phone Number",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                PhoneNumberField(
                    phone = phone,
                    onPhoneChange = { phone = it }
                )


                Spacer(modifier = Modifier.height(24.dp))

                AppButton(
                    text = "Get OTP",
                    onClick = {

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
                    background = PrimaryGreen
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                    onClick = { }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("New User? ")
                    Text(
                        text = "Create Account",
                        color = PrimaryGreen,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            navController.navigate("signup")
                        }
                    )
                }


            }
        }
    }
}
@Composable
fun PhoneNumberField(
    phone: String,
    onPhoneChange: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        placeholder = { Text("9876543210") },
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
}


@Composable
fun OrDivider() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f), color = DividerColor)
        Text(
            text = "  OR  ",
            fontSize = 12.sp,
            color = TextSecondary
        )
        Divider(modifier = Modifier.weight(1f), color = DividerColor)
    }
}

@Composable
fun SocialButton(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(38.dp)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}



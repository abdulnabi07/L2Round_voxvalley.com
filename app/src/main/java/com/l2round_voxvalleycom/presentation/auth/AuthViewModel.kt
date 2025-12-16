package com.l2round_voxvalleycom.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.l2round_voxvalleycom.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private var verificationId: String? = null

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            loading = true
            error = null

            val result = signInWithGoogleUseCase(idToken)

            loading = false

            result
                .onSuccess { onSuccess() }
                .onFailure { error = it.message }
        }
    }

    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: () -> Unit
    ) {
        Log.d("OTP_FLOW", "sendOtp() called for $phoneNumber")

        loading = true
        error = null

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("OTP_FLOW", "Auto verification completed")
                    signInWithPhoneCredential(credential) {
                        Log.d("OTP_FLOW", "Auto sign-in success")
                        onCodeSent()
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    loading = false
                    error = e.message
                    Log.e("OTP_FLOW", "OTP verification failed", e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    loading = false
                    this@AuthViewModel.verificationId = verificationId
                    Log.d("OTP_FLOW", "OTP code sent successfully")
                    onCodeSent()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit
    ) {
        Log.d("OTP_VERIFY", "verifyOtp() called")

        val id = verificationId
        if (id == null) {
            Log.e("OTP_VERIFY", "verificationId is NULL")
            error = "OTP expired. Please try again."
            return
        }

        val credential = PhoneAuthProvider.getCredential(id, otp)
        Log.d("OTP_VERIFY", "PhoneAuthCredential created")

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    Log.d("OTP_VERIFY", "Firebase sign-in SUCCESS")
                    onSuccess()
                } else {
                    Log.e(
                        "OTP_VERIFY",
                        "Firebase sign-in FAILED",
                        task.exception
                    )
                    error = task.exception?.message
                }
            }
    }


    private fun signInWithPhoneCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit
    ) {
        loading = true
        error = null

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    error = task.exception?.message
                }
            }
    }
}

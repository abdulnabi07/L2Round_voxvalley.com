package com.l2round_voxvalleycom.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.l2round_voxvalleycom.domain.model.User
import com.l2round_voxvalleycom.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential =
                GoogleAuthProvider.getCredential(idToken, null)

            val authResult =
                firebaseAuth.signInWithCredential(credential).await()

            val firebaseUser = authResult.user?:
            return Result.failure(Exception("Firebase user null"))

            Result.success(
                User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName,
                    email = firebaseUser.email
                )
            )
        } catch (e: Exception) {
            Log.e("FIREBASE_AUTH", "Firebase login failed", e)
            Result.failure(e)
        }
    }




}

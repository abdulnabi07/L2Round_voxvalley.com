package com.l2round_voxvalleycom.domain.repository

import com.l2round_voxvalleycom.domain.model.User

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<User>


}

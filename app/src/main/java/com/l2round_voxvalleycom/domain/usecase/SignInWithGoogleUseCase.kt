package com.l2round_voxvalleycom.domain.usecase

import com.l2round_voxvalleycom.domain.model.User
import com.l2round_voxvalleycom.domain.repository.AuthRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject


// In a generic UseCaseModule.kt
@Module
@InstallIn(ViewModelComponent::class)
class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<User> {
        return repository.signInWithGoogle(idToken)
    }
}

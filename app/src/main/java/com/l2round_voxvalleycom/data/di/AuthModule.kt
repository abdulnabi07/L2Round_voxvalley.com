package com.l2round_voxvalleycom.data.di

import com.google.firebase.auth.FirebaseAuth
import com.l2round_voxvalleycom.data.auth.AuthRepositoryImpl
import com.l2round_voxvalleycom.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository = impl
}

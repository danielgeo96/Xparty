package com.example.xparty.data.repository.firebase

import com.example.xparty.data.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun AuthRepositoryFirebaseImpl(authRepositoryFirebase: AuthRepositoryFirebase):AuthRepository
}
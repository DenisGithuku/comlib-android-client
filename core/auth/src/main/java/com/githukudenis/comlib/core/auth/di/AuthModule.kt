package com.githukudenis.comlib.core.auth.di

import com.githukudenis.comlib.core.auth.repository.AuthRepositoryImpl
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Binds
    fun bindAuthRepository(
        userApi: UserApi,
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(userApi, firebaseAuth)
}
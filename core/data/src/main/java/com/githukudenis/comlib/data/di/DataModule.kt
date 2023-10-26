package com.githukudenis.comlib.data.di

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.AuthRepositoryImpl
import com.githukudenis.comlib.data.repository.UserRepository
import com.githukudenis.comlib.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
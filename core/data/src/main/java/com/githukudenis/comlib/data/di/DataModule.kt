package com.githukudenis.comlib.data.di

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.AuthRepositoryImpl
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
}
package com.githukudenis.comlib.core.data_test

import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.fake.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
abstract class TestModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepository: FakeAuthRepository
    ): AuthRepository
}

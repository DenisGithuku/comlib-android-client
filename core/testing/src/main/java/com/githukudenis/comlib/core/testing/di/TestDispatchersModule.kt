package com.githukudenis.comlib.core.testing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

@Module
@InstallIn(SingletonComponent::class)
object TestDispatchersModule {

    @Provides
    fun provideIODispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher = testDispatcher

    @Provides
    fun provideDefaultDispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher = testDispatcher
}
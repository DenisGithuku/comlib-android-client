package com.githukudenis.comlib.core.common.di

import android.content.Context
import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.common.ComlibConnectivityManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): ComlibCoroutineDispatchers {
        return ComlibCoroutineDispatchers(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            main = Dispatchers.Main,
            unconfined = Dispatchers.Unconfined
        )
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ComlibConnectivityManager {
        return ComlibConnectivityManagerImpl(context = context)
    }
}

data class ComlibCoroutineDispatchers(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
)
package com.githukudenis.comlib.core.network.di

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.network.BooksApi
import com.githukudenis.comlib.core.network.GenresApi
import com.githukudenis.comlib.core.network.ImagesRemoteDataSource
import com.githukudenis.comlib.core.network.UserApi
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserApi(
        httpClient: HttpClient,
        dispatchers: ComlibCoroutineDispatchers): UserApi = UserApi(httpClient, dispatchers)

    @Provides
    @Singleton
    fun provideBooksApi(httpClient: HttpClient, dispatchers: ComlibCoroutineDispatchers): BooksApi = BooksApi(httpClient, dispatchers)

    @Provides
    @Singleton
    fun provideGenresApi(httpClient: HttpClient, dispatchers: ComlibCoroutineDispatchers): GenresApi = GenresApi(httpClient, dispatchers)

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideImageDataSource(
        storage: FirebaseStorage,
        dispatchers: ComlibCoroutineDispatchers
    ): ImagesRemoteDataSource = ImagesRemoteDataSource(storage,dispatchers)
}
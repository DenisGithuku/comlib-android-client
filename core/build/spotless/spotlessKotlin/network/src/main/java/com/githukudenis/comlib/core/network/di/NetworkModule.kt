
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.network.di

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.network.BooksApi
import com.githukudenis.comlib.core.network.GenresApi
import com.githukudenis.comlib.core.network.ImagesRemoteDataSource
import com.githukudenis.comlib.core.network.UserApi
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton fun provideUserApi(httpClient: HttpClient): UserApi = UserApi(httpClient)

    @Provides @Singleton fun provideBooksApi(httpClient: HttpClient): BooksApi = BooksApi(httpClient)

    @Provides
    @Singleton
    fun provideGenresApi(httpClient: HttpClient, dispatchers: ComlibCoroutineDispatchers): GenresApi =
        GenresApi(httpClient, dispatchers)

    @Provides @Singleton fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideImageDataSource(
        storage: FirebaseStorage,
        dispatchers: ComlibCoroutineDispatchers
    ): ImagesRemoteDataSource = ImagesRemoteDataSource(storage, dispatchers)
}


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
package com.githukudenis.comlib.data.di

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.data.utils.FirebaseStorageHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val TIMEOUT = 60_000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        val httpClient =
            HttpClient(Android) {
                install(JsonFeature) {
                    serializer =
                        KotlinxSerializer(
                            Json {
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )

                    engine {
                        connectTimeout = TIMEOUT
                        socketTimeout = TIMEOUT
                    }
                }

                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                Timber.tag("Logger Ktor => ").v(message)
                            }
                        }
                    level = LogLevel.NONE
                }

                install(ResponseObserver) {
                    onResponse { response -> Timber.tag("HTTP status: ").d("${response.status.value}") }
                }

                install(DefaultRequest) { header(HttpHeaders.ContentType, ContentType.Application.Json) }
            }
        return httpClient
    }

    @Provides
    @Singleton
    fun provideImageStorage(
        connectivityManager: ComlibConnectivityManager
    ): FirebaseStorageHandlerImpl {
        return FirebaseStorageHandlerImpl(connectivityManager)
    }
}

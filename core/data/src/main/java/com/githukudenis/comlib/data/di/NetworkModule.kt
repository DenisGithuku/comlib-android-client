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

import android.content.Context
import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import com.githukudenis.comlib.data.utils.FirebaseStorageHandlerImpl
import com.githukudenis.comlib.data.utils.NetworkInterceptor
import com.githukudenis.comlib.data.utils.NetworkMonitor
import com.githukudenis.comlib.data.utils.NetworkMonitorImpl
import com.githukudenis.comlib.data.utils.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 60_000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkImpl(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitorImpl(context)

    @Provides
    @Singleton
    fun provideTokenInterceptor(userPrefsDataSource: UserPrefsDatasource): TokenInterceptor = TokenInterceptor(userPrefsDataSource)

    @Provides
    @Singleton
    fun provideHttpClient(
        networkMonitor: NetworkMonitor, tokenInterceptor: TokenInterceptor
    ): HttpClient {
        val httpClient = HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(json = Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }


            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            engine {
                config {
                    retryOnConnectionFailure(true)
                    connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    addInterceptor(NetworkInterceptor(networkMonitor))

                    // Add a token change aware interceptor to prevent null value exceptions
                    addInterceptor(tokenInterceptor)
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor => ").v(message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response -> Timber.tag("HTTP status: ").d("${response.status.value}") }
            }

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
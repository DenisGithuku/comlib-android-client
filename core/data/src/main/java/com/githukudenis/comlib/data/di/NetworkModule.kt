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
import android.util.Log
import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import com.githukudenis.comlib.data.utils.FirebaseStorageHandlerImpl
import com.githukudenis.comlib.data.utils.NetworkInterceptor
import com.githukudenis.comlib.data.utils.NetworkMonitor
import com.githukudenis.comlib.data.utils.NetworkMonitorImpl
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
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

private const val TIMEOUT = 60_000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkImpl(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitorImpl(context)

    @OptIn(InternalAPI::class)
    @Provides
    @Singleton
    fun provideHttpClient(
        networkMonitor: NetworkMonitor, userPrefsDataSource: UserPrefsDatasource
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

            val accessToken = runBlocking { userPrefsDataSource.userPrefs.map { it.token }.first() }
            Log.d("token", accessToken.toString())
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }

            engine {
                config {
                    addInterceptor(NetworkInterceptor(networkMonitor))
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor => ").v(message)
                    }
                }
                level = LogLevel.BODY
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
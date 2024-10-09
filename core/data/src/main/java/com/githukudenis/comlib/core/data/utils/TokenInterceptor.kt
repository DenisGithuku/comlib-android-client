
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
package com.githukudenis.comlib.core.data.utils

import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import io.ktor.http.HttpHeaders
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @param userPrefsDatasource [UserPrefsDatasource] Provide api for getting the token from the
 *   datastore *
 */
class TokenInterceptor @Inject constructor(private val userPrefsDatasource: UserPrefsDatasource) :
    Interceptor {

    /** Token change aware interceptor to prevent null value exceptions */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the token from the datastore
        val token = runBlocking { userPrefsDatasource.userPrefs.mapLatest { it.token }.first() }

        // Add the token to the request headers
        val newRequest =
            chain.request().newBuilder().addHeader(HttpHeaders.Authorization, "Bearer $token").build()
        return chain.proceed(newRequest)
    }
}

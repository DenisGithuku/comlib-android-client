package com.githukudenis.comlib.data.utils

import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


/**
* @param userPrefsDatasource [UserPrefsDatasource]  Provide api for getting the token from the datastore
 * **/
class TokenInterceptor @Inject constructor(
    private val userPrefsDatasource: UserPrefsDatasource
): Interceptor {

    /**
     * Token change aware interceptor to prevent null value exceptions
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the token from the datastore
        val token = runBlocking { userPrefsDatasource.userPrefs.mapLatest { it.token }.first() }

        // Add the token to the request headers
        val newRequest = chain.request().newBuilder()
            .addHeader(HttpHeaders.Authorization, "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}
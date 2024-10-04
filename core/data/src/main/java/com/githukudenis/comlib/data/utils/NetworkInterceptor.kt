package com.githukudenis.comlib.data.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * NetworkMonitor is a interface meant to act as the API to all network monitoring related issues
 *
 * @param isConnected a method used to determine if the application is connected to a network or not
 */
interface NetworkMonitor {
    fun isConnected(): Boolean
}

/**
 * NetworkMonitorImpl is a class meant to check the application's network connectivity
 *
 * @param context a [Context] object. This is what is used to get the system's connectivity manager
 */
class NetworkMonitorImpl(private val context: Context) : NetworkMonitor {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }
}

/**
 * NetworkInterceptor is an
 * [application-interceptor](https://square.github.io/okhttp/features/interceptors/#application-interceptors)
 * meant to first check the status of the Network before sending the request
 *
 * @param networkMonitor a [NetworkMonitor] implementation that handles all of the actual network
 *   logic checking
 */
class NetworkInterceptor @Inject constructor(private val networkMonitor: NetworkMonitor) :
    Interceptor {

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (networkMonitor.isConnected()) {
            return chain.proceed(request)
        } else {
            throw NoNetworkException(
                "It looks like you're offline. Please check your internet connection and try again."
            )
        }
    }
}

/**
* Custom implementation of a specific network exception
**/
class NoNetworkException(message: String) : IOException(message)

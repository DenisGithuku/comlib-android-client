package com.githukudenis.comlib.core.common

import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException

sealed interface DataResult<out T : Any> {
    data class Loading<out T : Any>(val data: T?) : DataResult<T>
    data object Empty: DataResult<Nothing>
    data class Success<out T : Any>(val data: T) : DataResult<T>
    data class Error<out T : Any>(val message: String, val t: Throwable?) : DataResult<T>
}

suspend fun <T : Any> dataResultSafeApiCall(
    apiCall: suspend () -> T
): DataResult<T> {
    return try {
//        DataResult.Loading(data = null)
        val result = apiCall.invoke()
        DataResult.Success(result)
    }
    catch (e: NoInternetException) {
        Timber.e(e)
        DataResult.Error(
            message = e.message,
            e
        )
    }
    catch (e: NoDataConnectionException) {
        Timber.e(e)
        DataResult.Error(
            message = e.message,
            e
        )
    }
    catch(e: IOException) {
        Timber.e(e)
        DataResult.Error(
            message = "Oops! Looks like you're not connected",
            e
        )
    }
    catch(e: UnknownHostException) {
        Timber.e(e)
        DataResult.Error(
            message = "Could not connect to service. Please check your internet connection!",
            e
        )
    }
    catch (t: Throwable) {
        Timber.e(t)
        DataResult.Error(t.message ?: "An unknown error occurred", t)
    }
}
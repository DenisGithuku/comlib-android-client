package com.githukudenis.comlib.core.model

import timber.log.Timber

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
    } catch (t: Throwable) {
        Timber.e(t)
        DataResult.Error(t.message ?: "An unknown error occurred", t)
    }
}
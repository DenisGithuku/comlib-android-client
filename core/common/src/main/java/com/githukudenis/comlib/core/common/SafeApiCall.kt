package com.githukudenis.comlib.core.common

import timber.log.Timber

suspend fun <T> safeApiCall(block: suspend () -> ResponseResult<T>): ResponseResult<T> {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        ResponseResult.Failure(e)
    }
}

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure<out T>(val error: Throwable) : ResponseResult<T>()
}

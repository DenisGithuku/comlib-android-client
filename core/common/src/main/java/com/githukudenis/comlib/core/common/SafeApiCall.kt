package com.githukudenis.comlib.core.common

import timber.log.Timber
import java.net.UnknownHostException

suspend fun <T> safeApiCall(block: suspend () -> ResponseResult<T>): ResponseResult<T> {
    return try {
        block.invoke()
    } catch(e: UnknownHostException) {
        Timber.e(e)
        ResponseResult.Failure(Throwable(message = "Could not connect to service. Please check your internet connection!", cause = e))
    }
    catch (e: Exception) {
        Timber.e(e)
        ResponseResult.Failure(e)
    }
}

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure<out T>(val error: Throwable) : ResponseResult<T>()
}

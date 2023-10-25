package com.githukudenis.comlib.core.common

import timber.log.Timber

suspend fun <T> safeApiCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        throw e
    }
}


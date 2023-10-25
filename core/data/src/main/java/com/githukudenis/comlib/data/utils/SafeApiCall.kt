package com.githukudenis.comlib.data.utils

import timber.log.Timber

suspend fun <T> safeApiCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        throw e
    }
}


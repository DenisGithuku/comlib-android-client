package com.githukudenis.comlib.data.utils

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.common.NetworkStatus
import com.githukudenis.comlib.core.common.OperationException
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface FirebaseStorageOperationHandler {
    suspend fun <T> execute(operation: suspend () -> T ): Result<T>
}

class FirebaseStorageHandlerImpl @Inject constructor(
    private val connectivityManager: ComlibConnectivityManager
): FirebaseStorageOperationHandler {
    override suspend fun <T> execute(operation: suspend () -> T): Result<T> {
        return try {
            Result.success(operation.invoke())
        } catch (exception: StorageException) {
            val error = FirebaseExceptionFactory.createError(exception)
            Result.failure(error)
        } catch (exception: Exception) {
            val netStatus: Boolean = when(connectivityManager.observe().first()) {
                NetworkStatus.Lost -> false
                NetworkStatus.Available -> true
                NetworkStatus.Unavailable -> false
                NetworkStatus.Losing -> false
            }
            if (!netStatus) {
                Result.failure(OperationException.NoInternetException(exception))
            } else {
                Result.failure(OperationException.UnknownException(exception))
            }
        }
    }
}
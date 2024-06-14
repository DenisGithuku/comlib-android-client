package com.githukudenis.comlib.data.utils

import com.githukudenis.comlib.core.common.CLibException
import com.githukudenis.comlib.core.common.OperationException
import com.google.firebase.storage.StorageException

object FirebaseExceptionFactory {
    fun createError(exception: StorageException): CLibException {
        return when (exception.errorCode) {
            StorageException.ERROR_NOT_AUTHENTICATED -> {
                OperationException.AuthenticationException(exception)
            }

            StorageException.ERROR_NOT_AUTHORIZED -> {
                OperationException.UnauthorizedException(exception)
            }

            else -> OperationException.UnknownException(exception)
        }
    }
}
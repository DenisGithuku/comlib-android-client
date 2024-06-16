package com.githukudenis.comlib.core.common

sealed class CLibException(
    val reason: String,
    cause: Throwable?
): Exception(reason, cause)

sealed class OperationException(
    message: String,
    reason: String,
    cause: Throwable?
): CLibException(reason, cause) {
    data class NoInternetException(
        val throwable: Throwable? = null
    ): OperationException(
        message = "Device not connected to the internet",
        reason = "No internet",
        cause = throwable
    )

    data class UnauthorizedException(
        val throwable: Throwable? = null
    ): OperationException(
        message = "You are performed an unauthorized operation",
        reason = "Unauthorized operation",
        cause = throwable
    )

    data class AuthenticationException(
        val throwable: Throwable? = null
    ): OperationException(
        message = "You are not signed in",
        reason = "Not signed in",
        cause = throwable
    )

    data class UnknownException(
        val throwable: Throwable? = null
    ): OperationException(
        message = "An unknown error occurred",
        reason = "An error occurred",
        cause = throwable
    )
}

package com.githukudenis.comlib.core.common

import javax.net.ssl.SSLHandshakeException


object NoInternetException: Exception() {
    override fun getLocalizedMessage(): String? {
        return "Device not connected to internet"
    }

    override val message: String
        get() = "No internet"

    override val cause: Throwable?
        get() = super.cause
}

object NoDataConnectionException: SSLHandshakeException(
    "Could not reach servers"
) {
    override fun getLocalizedMessage(): String? {
        return "No data bundles"
    }

    override val message: String
        get() = "Could not reach servers"

    override val cause: Throwable?
        get() = super.cause

}

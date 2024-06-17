
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.common

sealed class CLibException(val reason: String, cause: Throwable?) : Exception(reason, cause)

sealed class OperationException(message: String, reason: String, cause: Throwable?) :
    CLibException(reason, cause) {
    data class NoInternetException(val throwable: Throwable? = null) :
        OperationException(
            message = "Device not connected to the internet",
            reason = "No internet",
            cause = throwable
        )

    data class UnauthorizedException(val throwable: Throwable? = null) :
        OperationException(
            message = "You are performed an unauthorized operation",
            reason = "Unauthorized operation",
            cause = throwable
        )

    data class AuthenticationException(val throwable: Throwable? = null) :
        OperationException(
            message = "You are not signed in",
            reason = "Not signed in",
            cause = throwable
        )

    data class UnknownException(val throwable: Throwable? = null) :
        OperationException(
            message = "An unknown error occurred",
            reason = "An error occurred",
            cause = throwable
        )
}

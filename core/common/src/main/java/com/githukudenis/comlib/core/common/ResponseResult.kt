
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

import java.net.UnknownHostException
import timber.log.Timber

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()

    data class Failure<out T>(val error: Throwable) : ResponseResult<T>()
}

suspend fun <T> safeApiCall(block: suspend () -> ResponseResult<T>): ResponseResult<T> {
    return try {
        block.invoke()
    } catch (e: UnknownHostException) {
        Timber.e(e)
        ResponseResult.Failure(
            Throwable(
                message = "Could not connect to service. Please check your internet connection!",
                cause = e
            )
        )
    } catch (e: OperationException.NoInternetException) {
        Timber.e(e)
        ResponseResult.Failure(Throwable(e.message))
    } catch (e: OperationException.AuthenticationException) {
        Timber.e(e)
        ResponseResult.Failure(Throwable(e.message))
    } catch (e: OperationException.UnauthorizedException) {
        Timber.e(e)
        ResponseResult.Failure(Throwable(e.message))
    } catch (e: Exception) {
        Timber.e(e)
        ResponseResult.Failure(e)
    }
}

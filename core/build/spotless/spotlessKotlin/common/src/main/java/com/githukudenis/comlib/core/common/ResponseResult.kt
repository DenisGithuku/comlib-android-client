
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

import io.ktor.client.call.receive
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import timber.log.Timber

suspend inline fun <reified T : Any> safeApiCall(block: () -> HttpResponse): ResponseResult<T> {
    return try {
        val response = block()
        if (!response.status.isSuccess()) {
            val error: ErrorResponse = response.receive()
            return ResponseResult.Failure(error = error)
        }
        val data: T = response.receive()
        ResponseResult.Success(data)
    } catch (e: Exception) {
        Timber.e(e)
        ResponseResult.Failure(
            ErrorResponse(status = "error", message = e.message ?: "An unknown error occurred")
        )
    }
}

sealed interface ResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseResult<T>

    data class Failure(val error: ErrorResponse) : ResponseResult<Nothing>
}

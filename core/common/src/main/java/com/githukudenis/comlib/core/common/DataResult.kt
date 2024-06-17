
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

import java.io.IOException
import java.net.UnknownHostException
import timber.log.Timber

sealed interface DataResult<out T : Any> {
    data class Loading<out T : Any>(val data: T?) : DataResult<T>

    data object Empty : DataResult<Nothing>

    data class Success<out T : Any>(val data: T) : DataResult<T>

    data class Error<out T : Any>(val message: String, val t: Throwable?) : DataResult<T>
}

suspend fun <T : Any> dataResultSafeApiCall(apiCall: suspend () -> T): DataResult<T> {
    return try {
        //        DataResult.Loading(data = null)
        val result = apiCall.invoke()
        DataResult.Success(result)
    } catch (e: OperationException.NoInternetException) {
        Timber.e(e)
        DataResult.Error(message = e.message ?: "An error occurred", t = e.throwable)
    } catch (e: OperationException.AuthenticationException) {
        Timber.e(e)
        DataResult.Error(message = e.message ?: "An error occurred", t = e.throwable)
    } catch (e: OperationException.UnauthorizedException) {
        Timber.e(e)
        DataResult.Error(message = e.message ?: "An error occurred", t = e)
    } catch (e: IOException) {
        Timber.e(e)
        DataResult.Error(message = "Oops! Looks like you're not connected", t = e)
    } catch (e: UnknownHostException) {
        Timber.e(e)
        DataResult.Error(
            message = "Could not connect to service. Please check your internet connection!",
            t = e
        )
    } catch (t: Throwable) {
        Timber.e(t)
        DataResult.Error(message = t.message ?: "An unknown error occurred", t = t)
    }
}

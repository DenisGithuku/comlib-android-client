
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
package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.user.DeleteUserResponse
import com.githukudenis.comlib.core.model.user.SingleUserResponse
import com.githukudenis.comlib.core.model.user.UpdateUserResponse
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import javax.inject.Inject

class UserApi @Inject constructor(private val httpClient: HttpClient) {

    suspend fun getUserById(userId: String): ResponseResult<SingleUserResponse> {
        return safeApiCall { httpClient.get(urlString = Endpoints.Users.GetById(userId).url) }
    }

    suspend fun updateUser(user: User): ResponseResult<UpdateUserResponse> {
        return safeApiCall {
            user.id?.let {
                httpClient.patch(urlString = Endpoints.Users.Update(it).url) { setBody(user) }
            } ?: throw IllegalArgumentException("User id cannot be null")
        }
    }

    suspend fun deleteUser(userId: String): ResponseResult<DeleteUserResponse> {
        return safeApiCall { httpClient.delete(urlString = Endpoints.Users.Delete(userId).url) }
    }
}

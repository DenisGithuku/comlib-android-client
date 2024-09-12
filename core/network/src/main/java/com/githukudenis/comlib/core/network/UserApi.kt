
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

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.user.SingleUserResponse
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserApi
@Inject
constructor(
    private val httpClient: HttpClient,
    private val dispatchers: ComlibCoroutineDispatchers
) {

    suspend fun addUser(user: User): String {
        return withContext(dispatchers.io) {
            val response = httpClient.post<User>(Endpoints.Users.url) { body = user }
            response.id ?: ""
        }
    }

    suspend fun getUsersInClub(clubId: String): List<User> {
        return withContext(dispatchers.io) { httpClient.get(Endpoints.Users.url) }
    }

    suspend fun getUserById(userId: String): SingleUserResponse {
        return withContext(dispatchers.io) { httpClient.get(urlString = Endpoints.User(userId).url) }
    }

    suspend fun updateUser(id: String, user: User) {
        withContext(dispatchers.io) {
            httpClient.patch<User>(urlString = Endpoints.User(id ?: return@withContext).url) {
                contentType(ContentType.Application.Json)
                body = user
            }
        }
    }

    suspend fun deleteUser(userId: String) {
        withContext(dispatchers.io) { httpClient.delete<User>(urlString = Endpoints.User(userId).url) }
    }
}

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
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserApi @Inject constructor(
    private val httpClient: HttpClient,
    private val dispatchers: ComlibCoroutineDispatchers
) {

    suspend fun addUser(user: User) {
        withContext(dispatchers.io) {
            httpClient.post<User>(
                Endpoints.Users.url
            ) {
                body = user
            }
        }
    }

    suspend fun getUsersInClub(clubId: String): List<User> {
        return withContext(dispatchers.io) {
            httpClient.get(
               Endpoints.Users.url
            )
        }
    }

    suspend fun getUserById(userId: String): SingleUserResponse {
        return withContext(dispatchers.io) {
            httpClient.get(
                urlString = Endpoints.User(userId).url
            )
        }
    }

    suspend fun updateUser(user: User) {
        withContext(dispatchers.io) {
            httpClient.patch<User>(
                urlString = Endpoints.Users.url
            ) {
                body = user
            }
        }
    }

    suspend fun deleteUser(userId: String) {
        withContext(dispatchers.io) {
            httpClient.delete<User>(
                urlString = Endpoints.User(userId).url
            )
        }
    }
}
package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.User
import com.githukudenis.comlib.data.common.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import javax.inject.Inject

class UserApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun addUser(user: User) {
        httpClient.post<User>(
            urlString = "${Constants.BASE_URL}/api/v1/users"
        ) {
            body = user
        }
    }

    suspend fun getUsersInClub(clubId: String): List<User> {
        return httpClient.get(
            urlString = "${Constants.BASE_URL}/api/v1/users"
        )
    }

    suspend fun getUserById(userId: String): User {
        return httpClient.get(
            urlString = "${Constants.BASE_URL}/api/v1/users/$userId"
        )
    }

    suspend fun updateUser(user: User) {
        httpClient.patch<User>(
            urlString = "${Constants.BASE_URL}/api/v1/users/${user.id}"
        ) {
            body = user
        }
    }

    suspend fun deleteUser(userId: String) {
        httpClient.delete<User>(
            urlString = "${Constants.BASE_URL}/api/v1/users/${userId}"
        )
    }
}
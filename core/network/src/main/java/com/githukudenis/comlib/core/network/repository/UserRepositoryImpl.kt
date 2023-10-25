package com.githukudenis.comlib.core.network.repository

import com.githukudenis.comlib.core.model.User
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.data.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.data.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val dispatchers: ComlibCoroutineDispatchers
): UserRepository {
    override suspend fun getUsersByClub(clubId: String): List<User> {
        return withContext(dispatchers.io) {
            userApi.getUsersInClub(clubId)
        }
    }

    override suspend fun addNewUser(user: User) {
        withContext(dispatchers.io) {
            userApi.addUser(user)
        }
    }

    override suspend fun updateUser(user: User) {
        withContext(dispatchers.io) {
            userApi.updateUser(user)
        }
    }

    override suspend fun getUserById(userId: String): User {
        return withContext(dispatchers.io) {
            userApi.getUserById(userId)
        }
    }

    override suspend fun deleteUser(userId: String) {
        withContext(dispatchers.io) {
            userApi.deleteUser(userId)
        }
    }
}
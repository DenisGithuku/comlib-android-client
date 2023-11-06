package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.network.UserApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val dispatchers: ComlibCoroutineDispatchers
): UserRepository {
    override suspend fun getUsersByClub(clubId: String): ResponseResult<List<User>> {
        return withContext(dispatchers.io) {
            try {
                val users = userApi.getUsersInClub(clubId)
                ResponseResult.Success(users)
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }

        }
    }

    override suspend fun addNewUser(user: User): ResponseResult<String> {
        return withContext(dispatchers.io) {
            try {
                val users = userApi.addUser(user)
                ResponseResult.Success("User added successfully")
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }
        }
    }

    override suspend fun updateUser(user: User): ResponseResult<String> {
        return withContext(dispatchers.io) {
            try {
                val users = userApi.updateUser(user)
                ResponseResult.Success("User added successfully")
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }
        }
    }

    override suspend fun getUserById(userId: String): ResponseResult<User> {
        return withContext(dispatchers.io) {
            try {
                val user = userApi.getUserById(userId)
                ResponseResult.Success(user)
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }
        }
    }

    override suspend fun deleteUser(userId: String) {
        withContext(dispatchers.io) {
            userApi.deleteUser(userId)
        }
    }
}
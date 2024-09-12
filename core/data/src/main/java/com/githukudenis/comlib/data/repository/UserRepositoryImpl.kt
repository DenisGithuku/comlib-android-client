
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
package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.network.UserApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(private val userApi: UserApi, private val dispatchers: ComlibCoroutineDispatchers) :
    UserRepository {
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
                val userId = userApi.addUser(user)
                ResponseResult.Success(userId)
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }
        }
    }

    override suspend fun updateUser(id: String, user: User): ResponseResult<String> {
        return withContext(dispatchers.io) {
            try {
                userApi.updateUser(id, user)
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
                val user = userApi.getUserById(userId).data.user
                ResponseResult.Success(user)
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) throw e
                ResponseResult.Failure(e)
            }
        }
    }

    override suspend fun deleteUser(userId: String) {
        withContext(dispatchers.io) { userApi.deleteUser(userId) }
    }
}

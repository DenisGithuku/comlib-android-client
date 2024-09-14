
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
package com.githukudenis.comlib.data.repository.fake

import android.net.Uri
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository

class FakeUserRepository : UserRepository {

    val users =
        (1..10)
            .map {
                User(
                    _id = "owner@$it",
                    clubs = listOf("club1", "club2", "club3"),
                    currentBooks = listOf(),
                    email = "$it@gmail.com",
                    preferredGenres = listOf(),
                    authId = "$it",
                    firstname = "$it.firstname",
                    id = "owner@$it",
                    lastname = "$it.lastname"
                )
            }
            .toMutableList()

    override suspend fun getUsersByClub(clubId: String): ResponseResult<List<User>> {
        return ResponseResult.Success(data = users.filter { clubId in it.clubs })
    }

    override suspend fun addNewUser(user: User): ResponseResult<String> {
        return try {
            users.add(user)
            ResponseResult.Success(data = "success")
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun updateUser(id: String, user: User): ResponseResult<String> {
        return try {
            val pos = users.indexOf(users.find { it.id == id })
            users[pos] = user
            ResponseResult.Success("success")
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun getUserById(userId: String): ResponseResult<User> {
        return try {
            ResponseResult.Success(users.first { it.id == userId })
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun deleteUser(userId: String) {
        users.removeIf { it.id == userId }
    }

    override suspend fun uploadUserImage(imageUri: Uri, userId: String): ResponseResult<String> {
        return try {
            val pos = users.indexOf(users.find { it.id == userId })
            users.set(pos, users[pos].copy(image = imageUri.lastPathSegment))
            ResponseResult.Success("success")
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }
}

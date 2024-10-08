
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
package com.githukudenis.comlib.core.data.repository.fake

import android.net.Uri
import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.Data
import com.githukudenis.comlib.core.model.user.DeactivateUserResponse
import com.githukudenis.comlib.core.model.user.DeleteUserResponse
import com.githukudenis.comlib.core.model.user.SingleUserResponse
import com.githukudenis.comlib.core.model.user.UpdateUserResponse
import com.githukudenis.comlib.core.model.user.User

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
                    firstname = "$it.firstname",
                    id = "owner@$it",
                    lastname = "$it.lastname"
                )
            }
            .toMutableList()

    override suspend fun deactivateAccount(userId: String): ResponseResult<DeactivateUserResponse> {
        return try {
            users.removeIf { it.id == userId }
            ResponseResult.Success(DeactivateUserResponse(status = "success", message = "success"))
        } catch (e: Exception) {
            ResponseResult.Failure(ErrorResponse(message = "Could not deactivate user", status = "fail"))
        }
    }

    override suspend fun updateUser(user: User): ResponseResult<UpdateUserResponse> {
        return try {
            val pos = users.indexOf(users.find { it.id == user.id })
            users[pos] = user
            ResponseResult.Success(UpdateUserResponse(status = "success", message = "success"))
        } catch (e: Exception) {
            ResponseResult.Failure(
                ErrorResponse(message = "Could not complete_profile user", status = "fail")
            )
        }
    }

    override suspend fun getUserById(userId: String): ResponseResult<SingleUserResponse> {
        return try {
            ResponseResult.Success(
                SingleUserResponse(status = "success", data = Data(user = users.find { it.id == userId }!!))
            )
        } catch (e: Exception) {
            ResponseResult.Failure(ErrorResponse(message = "User not found", status = "fail"))
        }
    }

    override suspend fun deleteUser(userId: String): ResponseResult<DeleteUserResponse> {
        return try {
            users.removeIf { it.id == userId }
            ResponseResult.Success(DeleteUserResponse(status = "success", message = "success"))
        } catch (e: Exception) {
            ResponseResult.Failure(ErrorResponse(message = "Could not delete user", status = "fail"))
        }
    }

    override suspend fun uploadUserImage(
        imageUri: Uri,
        userId: String,
        isNewUser: Boolean
    ): ResponseResult<String> {
        return try {
            val pos = users.indexOf(users.find { it.id == userId })
            users[pos] = users[pos].copy(image = imageUri.lastPathSegment)
            ResponseResult.Success("success")
        } catch (e: Exception) {
            ResponseResult.Failure(ErrorResponse(message = "Could not upload image", status = "fail"))
        }
    }
}

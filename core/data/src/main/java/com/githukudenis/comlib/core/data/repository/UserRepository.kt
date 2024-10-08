
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
package com.githukudenis.comlib.core.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.DeactivateUserResponse
import com.githukudenis.comlib.core.model.user.DeleteUserResponse
import com.githukudenis.comlib.core.model.user.SingleUserResponse
import com.githukudenis.comlib.core.model.user.UpdateUserResponse
import com.githukudenis.comlib.core.model.user.User

interface UserRepository {

    suspend fun updateUser(user: User): ResponseResult<UpdateUserResponse>

    suspend fun getUserById(userId: String): ResponseResult<SingleUserResponse>

    suspend fun deleteUser(userId: String): ResponseResult<DeleteUserResponse>

    suspend fun deactivateAccount(userId: String): ResponseResult<DeactivateUserResponse>

    suspend fun uploadUserImage(
        imageUri: Uri,
        userId: String,
        isNewUser: Boolean
    ): ResponseResult<String>
}

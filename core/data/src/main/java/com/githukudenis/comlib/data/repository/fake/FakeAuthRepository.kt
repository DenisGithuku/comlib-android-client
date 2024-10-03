
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

import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.AddUserResponse
import com.githukudenis.comlib.core.model.user.ResetPasswordResponse
import com.githukudenis.comlib.core.model.user.UserLoginDTO
import com.githukudenis.comlib.core.model.user.UserLoginResponse
import com.githukudenis.comlib.core.model.user.UserSignUpDTO
import com.githukudenis.comlib.data.repository.AuthRepository

class FakeAuthRepository : AuthRepository {

    val users: MutableList<UserSignUpDTO> = mutableListOf()

    override suspend fun signUp(userSignUpDTO: UserSignUpDTO): ResponseResult<AddUserResponse> {
        return try {
            users.add(userSignUpDTO)
            ResponseResult.Success(
                AddUserResponse(
                    token = "token",
                    status = "success",
                    id = "id"
                )
            )
        } catch (e: Exception) {
            ResponseResult.Failure(
                ErrorResponse(
                    message = "Could not sign up user",
                    status = "fail"
                )
            )
        }
    }

    override suspend fun login(
        userLogInDTO: UserLoginDTO,
        onSuccess: suspend (UserLoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val user = users.find { it.email == userLogInDTO.email }
            if (user == null) {
                onError("User not found")
                return
            }

        } catch (e: Exception) {
            onError(
                ErrorResponse(
                    message = "Could not login user",
                    status = "fail"
                ).message
            )
        }

    }

    override suspend fun signOut() {}

    override suspend fun resetPassword(
        email: String,
        onSuccess: (ResetPasswordResponse) -> Unit,
        onError: (String) -> Unit
    ) {

    }
}

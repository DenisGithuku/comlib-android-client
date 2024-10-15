
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

import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.model.user.AddUserResponse
import com.githukudenis.comlib.core.model.user.ResetPasswordResponse
import com.githukudenis.comlib.core.model.user.UserLoginDTO
import com.githukudenis.comlib.core.model.user.UserLoginResponse
import com.githukudenis.comlib.core.model.user.UserSignUpDTO

class FakeAuthRepository : AuthRepository {

    /** Fake data source for testing */
    private val users =
        mutableListOf(
            UserSignUpDTO(
                email = "william.henry.moody@my-own-personal-domain.com",
                password = "password",
                passwordConfirm = "password",
                firstname = "William",
                lastname = "Henry"
            ),
            UserSignUpDTO(
                email = "james.wilson@example-pet-store.com",
                password = "password",
                passwordConfirm = "password",
                firstname = "James",
                lastname = "Wilson"
            ),
            UserSignUpDTO(
                email = "alice.doe@example-pet-store.com",
                password = "password",
                passwordConfirm = "password",
                firstname = "Alice",
                lastname = "Doe"
            )
        )

    override suspend fun signUp(userSignUpDTO: UserSignUpDTO): ResponseResult<AddUserResponse> {
        return try {
            if (users.any { it.email == userSignUpDTO.email }) {
                return ResponseResult.Failure(
                    ErrorResponse(message = "User already exists", status = "fail")
                )
            }
            users.add(userSignUpDTO)
            ResponseResult.Success(AddUserResponse(token = "token", status = "success", id = "id"))
        } catch (e: Exception) {
            ResponseResult.Failure(ErrorResponse(message = "Could not sign up user", status = "fail"))
        }
    }

    override suspend fun login(
        userLogInDTO: UserLoginDTO,
        onSuccess: suspend (UserLoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userExists =
                users.any { it.email == userLogInDTO.email && it.password == userLogInDTO.password }
            if (userExists) {
                onSuccess(UserLoginResponse(token = "token", status = "success", id = "owner@5"))
            } else {
                onError("Could not find user with those details")
            }
        } catch (e: Exception) {
            onError(
                ErrorResponse(message = e.localizedMessage ?: "Unknown error", status = "fail").message
            )
        }
    }

    override suspend fun signOut() {}

    override suspend fun resetPassword(
        email: String,
        onSuccess: (ResetPasswordResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        if (users.none { it.email == email }) {
            onError("Couldn't find that user!")
        } else {
            onSuccess(ResetPasswordResponse(status = "success", message = "Password reset email sent"))
        }
    }
}

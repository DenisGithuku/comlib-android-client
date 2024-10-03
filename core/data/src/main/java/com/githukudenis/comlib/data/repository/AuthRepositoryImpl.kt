
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

import com.githukudenis.comlib.core.auth.di.AuthApi
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.AddUserResponse
import com.githukudenis.comlib.core.model.user.ResetPasswordResponse
import com.githukudenis.comlib.core.model.user.UserLoginDTO
import com.githukudenis.comlib.core.model.user.UserLoginResponse
import com.githukudenis.comlib.core.model.user.UserSignUpDTO
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val authApi: AuthApi,
    private val userPrefsRepository: UserPrefsRepository
) : AuthRepository {
    override suspend fun signUp(userSignUpDTO: UserSignUpDTO): ResponseResult<AddUserResponse> {
        return authApi.signUp(userSignUpDTO)
    }

    override suspend fun login(
        userLogInDTO: UserLoginDTO,
        onSuccess: suspend (UserLoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        return when(val result = authApi.login(userLogInDTO)) {
            is ResponseResult.Success -> onSuccess(result.data)
           is ResponseResult.Failure -> onError(result.error.message)
       }
    }

    override suspend fun signOut() {
        userPrefsRepository.clearSession()
    }

    override suspend fun resetPassword(
        email: String,
        onSuccess: (ResetPasswordResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        return when(val result = authApi.resetPassword(email)) {
            is ResponseResult.Success -> onSuccess(result.data)
            is ResponseResult.Failure -> onError(result.error.message)
        }
    }
}


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

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.model.user.User
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val authDataSource: AuthDataSource,
    private val userRepository: UserRepository
) : AuthRepository {
    override suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String?> {
        val result = authDataSource.signUpWithEmail(userAuthData)
        return when (result) {
            is ResponseResult.Failure -> result
            is ResponseResult.Success -> {
                val (email, firstname, lastname) = userAuthData
                userRepository.addNewUser(
                    User(email = email, firstname = firstname, lastname = lastname, authId = result.data)
                )
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        authDataSource.login(email, password, onSuccess, onError)
    }

    override suspend fun signOut() = authDataSource.signOut()

    override suspend fun resetPassword(
        email: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        authDataSource.resetPassword(email, onSuccess, onError)
    }
}

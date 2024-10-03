
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
package com.githukudenis.comlib.core.auth.di

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.UserLoginDTO
import com.githukudenis.comlib.core.model.UserSignUpDTO
import com.githukudenis.comlib.core.model.user.AddUserResponse
import com.githukudenis.comlib.core.model.user.ResetPasswordResponse
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import javax.inject.Inject

class AuthApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun login(userLoginDTO: UserLoginDTO): ResponseResult<AddUserResponse> {
        return safeApiCall { httpClient.post(urlString = Endpoints.Login.url) { body = userLoginDTO } }
    }

    suspend fun signUp(userSignUpDTO: UserSignUpDTO): ResponseResult<AddUserResponse> {
        return safeApiCall {
            httpClient.post(urlString = Endpoints.SignUp.url) { body = userSignUpDTO }
        }
    }

    suspend fun resetPassword(email: String): ResponseResult<ResetPasswordResponse> {
        return safeApiCall { httpClient.post(urlString = Endpoints.ResetPassword.url) { body = email } }
    }
}

package com.githukudenis.comlib.core.auth.di

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.user.AddUserResponse
import com.githukudenis.comlib.core.model.user.ResetPasswordResponse
import com.githukudenis.comlib.core.model.user.UserLoginDTO
import com.githukudenis.comlib.core.model.user.UserLoginResponse
import com.githukudenis.comlib.core.model.user.UserSignUpDTO
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class AuthApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun login(userLoginDTO: UserLoginDTO): ResponseResult<UserLoginResponse> {
        return safeApiCall {
            httpClient.post(urlString = Endpoints.Login.url) {
                setBody(userLoginDTO)
            }
        }
    }

    suspend fun signUp(userSignUpDTO: UserSignUpDTO): ResponseResult<AddUserResponse> {
        return safeApiCall {
            httpClient.post(urlString = Endpoints.SignUp.url) {
                setBody(userSignUpDTO)
            }
        }
    }

    suspend fun resetPassword(email: String): ResponseResult<ResetPasswordResponse> {
        return safeApiCall {
            httpClient.post(urlString = Endpoints.ResetPassword.url) {
                setBody(email)
            }
        }
    }
}
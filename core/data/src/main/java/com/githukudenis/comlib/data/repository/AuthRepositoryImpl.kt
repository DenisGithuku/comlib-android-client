package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.model.user.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userRepository: UserRepository
) : AuthRepository {
    override suspend fun signUpWithEmail(
        userAuthData: UserAuthData
    ): ResponseResult<String?> {
        val result = authDataSource.signUpWithEmail(userAuthData)
        return when (result) {
            is ResponseResult.Failure -> result
            is ResponseResult.Success -> {
                val (email, firstname, lastname) = userAuthData
                userRepository.addNewUser(
                        User(
                            email = email,
                            firstname = firstname,
                            lastname = lastname,
                            authId = result.data
                        )
                    )
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit) {
        authDataSource.login(email, password, onSuccess, onError)
    }

    override suspend fun signOut() = authDataSource.signOut()
    override suspend fun resetPassword(email: String) { authDataSource.resetPassword(email) }

}
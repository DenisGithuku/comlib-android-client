package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.model.UserAuthData
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
                val (email, firstname, lastname, age) = userAuthData
                userRepository.addNewUser(
                        User(
                            email = email,
                            firstname = firstname,
                            lastname = lastname,
                            age = age
                        )
                    )
            }
        }
    }

    override suspend fun login(email: String, password: String, onResult: (ResponseResult<String?>) -> Unit) {
        return authDataSource.login(email, password, onResult)
    }

    override suspend fun signOut() = authDataSource.signOut()
}
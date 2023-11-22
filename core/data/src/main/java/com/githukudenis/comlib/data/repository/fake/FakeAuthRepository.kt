package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.auth.di.FakeAuthDataSource
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.data.repository.AuthRepository

class FakeAuthRepository: AuthRepository {

    private val authDataSource: FakeAuthDataSource = FakeAuthDataSource()

    override suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String?> {
        return authDataSource.signUpWithEmail(userAuthData)
    }

    override suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        authDataSource.login(email, password, onSuccess, onError)
    }

    override suspend fun signOut() {

    }

    override suspend fun resetPassword(email: String) {

    }
}
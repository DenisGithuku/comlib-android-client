package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.data.repository.AuthRepository

class FakeAuthRepository: AuthRepository {
    override suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String) {
        TODO("Not yet implemented")
    }
}
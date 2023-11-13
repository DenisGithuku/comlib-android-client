package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData

interface AuthRepository {
    suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String?>

    suspend fun login(email: String, password: String, onResult: (Throwable?) -> Unit)

    suspend fun signOut()
    suspend fun resetPassword(email: String)
}
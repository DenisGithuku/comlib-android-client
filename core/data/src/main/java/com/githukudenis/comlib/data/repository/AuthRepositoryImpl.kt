package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.auth.AuthDataSource
import com.githukudenis.comlib.core.model.UserAuthData
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signInWithEmail(
        userAuthData: UserAuthData
    ): String? {
        return authDataSource.signInWithEmail(userAuthData)
    }

    override suspend fun login(userAuthData: UserAuthData): String? {
        return authDataSource.login(userAuthData)
    }

    override suspend fun signOut() = authDataSource.signOut()
}
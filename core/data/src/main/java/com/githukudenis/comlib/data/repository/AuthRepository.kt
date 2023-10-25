package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.UserAuthData

interface AuthRepository {
    suspend fun signInWithEmail(userAuthData: UserAuthData): String?

    suspend fun login(userAuthData: UserAuthData): String?

    suspend fun signOut()
}
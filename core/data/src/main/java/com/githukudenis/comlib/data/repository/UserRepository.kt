package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.User

interface UserRepository {
    suspend fun getUsersByClub(clubId: String): List<User>

    suspend fun addNewUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun getUserById(userId: String): User

    suspend fun deleteUser(userId: String)
}
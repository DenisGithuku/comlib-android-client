package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.User

interface UserRepository {
    suspend fun getUsersByClub(clubId: String): ResponseResult<List<User>>

    suspend fun addNewUser(user: User): ResponseResult<String>

    suspend fun updateUser(user: User): ResponseResult<String>

    suspend fun getUserById(userId: String): ResponseResult<User>

    suspend fun deleteUser(userId: String)
}
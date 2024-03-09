package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository

class FakeUserRepository: UserRepository {

    val users = (1..10).map {
        User(
            _id = "$it",
            clubs = listOf(
                "club1", "club2", "club3"
            ),
            currentBooks = listOf(),
            email = "$it@gmail.com",
            preferredGenres = listOf(),
            authId = "$it",
            firstname = "$it.name",
            id = "$it"
        )
    }.toMutableList()

    override suspend fun getUsersByClub(clubId: String): ResponseResult<List<User>> {
        return ResponseResult.Success(
            data = users.filter { clubId in it.clubs }
        )
    }

    override suspend fun addNewUser(user: User): ResponseResult<String> {
        return try {
            users.add(user)
            ResponseResult.Success(data = "success")
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun updateUser(user: User): ResponseResult<String> {
        return try {
            users.replaceAll {
                user
            }
            ResponseResult.Success("success")
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun getUserById(userId: String): ResponseResult<User> {
        return try {
            ResponseResult.Success(
                users.first { it.id == userId }
            )
        } catch(e: Exception) {
            ResponseResult.Failure(e)
        }
    }

    override suspend fun deleteUser(userId: String) {
        users.removeIf { it.id == userId }
    }
}
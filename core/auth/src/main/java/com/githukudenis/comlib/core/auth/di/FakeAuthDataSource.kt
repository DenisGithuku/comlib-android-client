package com.githukudenis.comlib.core.auth.di

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import kotlinx.coroutines.delay

/*
Fake data source for testing
 */

val users = listOf(
    UserAuthData(
        email = "william.henry.moody@my-own-personal-domain.com",
        password = "password",
        firstname = "William",
        lastname = "Henry"
    ),
    UserAuthData(
        email = "james.wilson@example-pet-store.com",
        password = "password",
        firstname = "James",
        lastname = "Wilson"
    ),
    UserAuthData(
        email = "alice.doe@example-pet-store.com",
        password = "password",
        firstname = "Alice",
        lastname = "Doe"
    ),
)

class FakeAuthDataSource {
    suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String> {
        delay(1000)
        return if (userAuthData in users) {
            ResponseResult.Failure(Throwable("User exists"))
        } else {
            ResponseResult.Success("userId")
        }
    }

    suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        if (users.any { user -> user.email == email && user.password == password }) {
            onSuccess("userId")
        } else {
            onError(Throwable("No user found"))
        }
    }
}
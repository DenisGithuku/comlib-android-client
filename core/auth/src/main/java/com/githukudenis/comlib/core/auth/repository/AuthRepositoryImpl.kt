package com.githukudenis.comlib.core.auth.repository

import com.githukudenis.comlib.core.model.User
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.utils.safeApiCall
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {
    override suspend fun signInWithEmail(
        userAuthData: UserAuthData
    ): String? {
        return safeApiCall {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                userAuthData.email,
                userAuthData.password
            ).await()

            if (result.user != null) {
                val (email, firstname, lastname, age ) = userAuthData
                userApi.addUser(User(email = email, firstname = firstname, lastname = lastname, age = age))
            }
            result.user?.uid
        }
    }

    override suspend fun login(userAuthData: UserAuthData): String? {
        return safeApiCall {
            val result =
                firebaseAuth.signInWithEmailAndPassword(userAuthData.email, userAuthData.password)
                    .await()
            result.user?.uid
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()
}
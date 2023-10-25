package com.githukudenis.comlib.core.auth

import com.githukudenis.comlib.core.model.User
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.common.safeApiCall
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val userApi: UserApi,
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: ComlibCoroutineDispatchers
) {
    suspend fun signInWithEmail(
        userAuthData: UserAuthData
    ): String? {
        return withContext(dispatcher.io) {
            safeApiCall {
                val result = firebaseAuth.createUserWithEmailAndPassword(
                    userAuthData.email,
                    userAuthData.password
                ).await()

                if (result.user != null) {
                    val (email, firstname, lastname, age) = userAuthData
                    userApi.addUser(
                        User(
                            email = email,
                            firstname = firstname,
                            lastname = lastname,
                            age = age
                        )
                    )
                }
                result.user?.uid
            }
        }
    }

    suspend fun login(userAuthData: UserAuthData): String? {
        return withContext(dispatcher.io) {
            safeApiCall {
                val result =
                    firebaseAuth.signInWithEmailAndPassword(
                        userAuthData.email,
                        userAuthData.password
                    )
                        .await()
                result.user?.uid
            }
        }
    }

    suspend fun signOut() = withContext(dispatcher.io) { firebaseAuth.signOut() }
}
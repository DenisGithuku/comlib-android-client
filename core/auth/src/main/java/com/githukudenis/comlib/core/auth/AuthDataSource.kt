package com.githukudenis.comlib.core.auth

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.network.UserApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val userApi: UserApi,
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: ComlibCoroutineDispatchers
) {
    suspend fun signUpWithEmail(
        userAuthData: UserAuthData
    ): ResponseResult<String?> {
        return withContext(dispatcher.io) {
            safeApiCall {
                val result = firebaseAuth.createUserWithEmailAndPassword(
                    userAuthData.email, userAuthData.password
                )

                if (result.exception != null) {
                    ResponseResult.Failure(
                        error = result.exception
                            ?: Throwable(message = "An error occurred. Couldn't sign up")
                    )
                } else {
                    ResponseResult.Success(data = result.await().user?.uid)
                }
            }
        }
    }

    suspend fun login(
        email: String,
        password: String,
        onSuccess: suspend (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        withContext(dispatcher.io) {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(
                    email, password
                ).await()
                onSuccess(result?.user?.uid!!)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    suspend fun signOut() = withContext(dispatcher.io) { firebaseAuth.signOut() }
    suspend fun resetPassword(email: String, onSuccess: (String) -> Unit, onError: (Throwable?) -> Unit) {
        withContext(dispatcher.io) {
            try {
                firebaseAuth.sendPasswordResetEmail(email)
                onSuccess("Password sent successfully!")
            } catch (t: Throwable) {
                onError(t)
            }
        }
    }
}
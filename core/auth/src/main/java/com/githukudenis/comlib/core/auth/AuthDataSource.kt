package com.githukudenis.comlib.core.auth

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.network.UserApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
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
        email: String, password: String, onResult: (ResponseResult<String?>) -> Unit
    ) {
        return withContext(dispatcher.io) {
            firebaseAuth.signInWithEmailAndPassword(
                email, password
            ).addOnCompleteListener { authResultTask ->
                if (!authResultTask.isSuccessful) {
                    Timber.tag("login error").d(authResultTask.exception)
                    val responseResult: ResponseResult<String?> = when (authResultTask.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            ResponseResult.Failure(
                                Throwable(message = "Invalid email or password.")
                            )
                        }

                        is FirebaseAuthInvalidUserException -> {
                            ResponseResult.Failure(
                                Throwable(message = "User doesn't exist. Create an account first!")
                            )
                        }

                        else -> ResponseResult.Failure(
                            Throwable(
                                message = authResultTask.exception?.message
                                    ?: "An unknown error occurred"
                            )
                        )
                    }
                    onResult(responseResult)
                } else {
                    onResult(ResponseResult.Success(data = authResultTask.result.user?.uid))
                }
            }
        }
    }

    suspend fun signOut() = withContext(dispatcher.io) { firebaseAuth.signOut() }
    suspend fun resetPassword(email: String): Boolean {
        return withContext(dispatcher.io) {
            val result = firebaseAuth.sendPasswordResetEmail(email)
            result.isSuccessful
        }
    }
}
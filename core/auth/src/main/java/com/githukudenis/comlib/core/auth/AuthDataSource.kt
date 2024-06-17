
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.auth

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.core.network.UserApi
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthDataSource
@Inject
constructor(
    private val userApi: UserApi,
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: ComlibCoroutineDispatchers
) {
    suspend fun signUpWithEmail(userAuthData: UserAuthData): ResponseResult<String?> {
        return withContext(dispatcher.io) {
            safeApiCall {
                val result =
                    firebaseAuth.createUserWithEmailAndPassword(userAuthData.email, userAuthData.password)

                if (result.exception != null) {
                    ResponseResult.Failure(
                        error = result.exception ?: Throwable(message = "An error occurred. Couldn't sign up")
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
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                onSuccess(result?.user?.uid!!)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    suspend fun signOut() = withContext(dispatcher.io) { firebaseAuth.signOut() }

    suspend fun resetPassword(
        email: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
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

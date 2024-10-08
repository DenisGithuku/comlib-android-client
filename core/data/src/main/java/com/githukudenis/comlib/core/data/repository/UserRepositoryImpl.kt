
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
package com.githukudenis.comlib.core.data.repository

import android.net.Uri
import android.util.Log
import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.user.DeactivateUserResponse
import com.githukudenis.comlib.core.model.user.DeleteUserResponse
import com.githukudenis.comlib.core.model.user.SingleUserResponse
import com.githukudenis.comlib.core.model.user.UpdateUserResponse
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.core.network.ImagesRemoteDataSource
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.core.network.common.FirebaseExt
import com.githukudenis.comlib.core.network.common.ImageStorageRef
import javax.inject.Inject
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepositoryImpl
@Inject
constructor(
    private val userApi: UserApi,
    private val dispatchers: ComlibCoroutineDispatchers,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) : UserRepository {

    override suspend fun updateUser(user: User): ResponseResult<UpdateUserResponse> {
        return withContext(dispatchers.io) { userApi.updateUser(user) }
    }

    override suspend fun deactivateAccount(userId: String): ResponseResult<DeactivateUserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String): ResponseResult<SingleUserResponse> {
        return withContext(dispatchers.io) { userApi.getUserById(userId) }
    }

    override suspend fun deleteUser(userId: String): ResponseResult<DeleteUserResponse> {
        return withContext(dispatchers.io) {
            when (val result = userApi.getUserById(userId)) {
                is ResponseResult.Failure -> {
                    ResponseResult.Failure(result.error)
                }
                is ResponseResult.Success -> {
                    result.data.data.user.image?.let {
                        imagesRemoteDataSource.deleteImage(imagePath = FirebaseExt.getFilePathFromUrl(it))
                    }
                    userApi.deleteUser(userId)
                }
            }
        }
    }

    override suspend fun uploadUserImage(
        imageUri: Uri,
        userId: String,
        isNewUser: Boolean
    ): ResponseResult<String> {
        return withContext(dispatchers.io) {
            // Step 1: Fetch user by ID
            when (val userResult = userApi.getUserById(userId)) {
                is ResponseResult.Failure -> {
                    Timber.e(userResult.error.message)
                    return@withContext ResponseResult.Failure(userResult.error)
                }
                is ResponseResult.Success -> {
                    val user = userResult.data.data.user
                    val currentImage = user.image

                    // Step 2: Delete existing image if the user is not new and has an image
                    if (!isNewUser && !currentImage.isNullOrEmpty()) {
                        Log.d("Image", "Deletes image....")
                        val imagePath = FirebaseExt.getFilePathFromUrl(currentImage)
                        Log.d("Image path", imagePath)
                        imagesRemoteDataSource.deleteImage(imagePath)
                    }

                    // Step 3: Upload new image
                    val imagePath = ImageStorageRef.Users(imageUri.lastPathSegment ?: "").ref
                    Log.d("Image add path", imagePath)
                    val uploadResult = imagesRemoteDataSource.addImage(imageUri, imagePath)

                    val uploadedImageUrl =
                        uploadResult.getOrNull()
                            ?: return@withContext ResponseResult.Failure(
                                ErrorResponse(message = "Could not upload image", status = "fail")
                            )

                    // Step 4: Return success with the new image URL
                    return@withContext ResponseResult.Success(uploadedImageUrl)
                }
            }
        }
    }
}

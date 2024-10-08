
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
package com.githukudenis.comlib.core.network

import android.net.Uri
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImagesRemoteDataSource
@Inject
constructor(firebaseStorage: FirebaseStorage, private val dispatchers: ComlibCoroutineDispatchers) {
    private val storageRef = firebaseStorage.reference

    suspend fun addImage(imageUri: Uri, path: String): Result<String?> {
        return withContext(dispatchers.io) {
            try {
                val url: String =
                    storageRef.child(path).putFile(imageUri).await().storage.downloadUrl.await().toString()
                Result.success(url)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteImage(imagePath: String): Result<Boolean> {
        return withContext(dispatchers.io) {
            try {
                storageRef.child(imagePath).delete()
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

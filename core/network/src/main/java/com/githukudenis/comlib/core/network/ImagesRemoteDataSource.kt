package com.githukudenis.comlib.core.network


import android.net.Uri
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.network.common.ImagesStorage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesRemoteDataSource @Inject constructor(
    firebaseStorage: FirebaseStorage,
    private val dispatchers: ComlibCoroutineDispatchers
) {
    private val storageRef = firebaseStorage.reference.child(ImagesStorage.ref)

    suspend fun addImage(imageUri: Uri): Result<String?> {
        return withContext(dispatchers.io) {
            try {
                val url: String = imageUri.lastPathSegment?.let { path ->
                    val imagePath = storageRef.child(path)
                    val result = imagePath.putFile(imageUri).await()
                    result.storage.downloadUrl.await().path
                } ?: ""
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
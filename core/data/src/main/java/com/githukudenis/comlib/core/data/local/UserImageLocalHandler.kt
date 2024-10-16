
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
package com.githukudenis.comlib.core.data.local

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserImageLocalHandler @Inject constructor(private val context: Context) {
    suspend fun saveImage(imageUrl: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Create a file name
                val fileName = "profile_image.jpg"

                // Create a directory if not exists in internal storage
                val directory = File(context.filesDir, "images")
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                // Create a file in the directory
                val imageFile = File(directory, fileName)

                // Check if file exists and delete it
                if (imageFile.exists()) {
                    imageFile.delete()
                }

                // Create request to download image
                val request = ImageRequest.Builder(context).data(imageUrl).build()

                // Use coil to execute request and save image to file
                val imageLoader = ImageLoader(context)
                val result = imageLoader.execute(request)

                // Check if result is successful and save image to file
                if (result is SuccessResult) {
                    // Get bitmap from request
                    val bitmap = result.drawable.toBitmap()

                    // Save the bitmap to the file
                    FileOutputStream(imageFile).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }

                    // Return URI from saved file
                    Uri.fromFile(imageFile).toString()
                } else {
                    // Handle error
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}

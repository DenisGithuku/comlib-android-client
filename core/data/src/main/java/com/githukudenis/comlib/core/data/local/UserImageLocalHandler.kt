
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
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UserImageLocalHandler @Inject constructor(private val context: Context) {
    suspend fun saveImage(imageUri: Uri): String? {
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

                // Open input stream from the image URI
                val inputStream = context.contentResolver.openInputStream(imageUri)

                // Write input stream to the file
                imageFile.outputStream().use { outputStream -> inputStream?.copyTo(outputStream) }

                // Return the absolute path of the saved/updated file
                imageFile.absolutePath
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}


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
package com.githukudenis.comlib.core.network.common

object Constants {
    const val BASE_URL = "https://comlib-api.onrender.com/"
}

sealed class ImageStorageRef(val ref: String) {
    data class Books(private val path: String) : ImageStorageRef(ref = "images/books/$path")

    data class Users(private val path: String) : ImageStorageRef(ref = "images/users/$path")
}

sealed class Endpoints(private val path: String) {
    val url: String
        get() {
            return buildString {
                append(Constants.BASE_URL)
                append(path)
            }
        }

    data object Books : Endpoints("api/v1/books")

    data class Book(private val id: String) : Endpoints("api/v1/books/$id")

    data object Users : Endpoints("api/v1/users")

    data class User(private val id: String) : Endpoints("api/v1/users/$id")

    data object Genres : Endpoints("api/v1/genres")

    data class Genre(private val id: String) : Endpoints("api/v1/genres/$id")
}

object FirebaseExt {
    fun getFilePathFromUrl(fileUrl: String): String {
        // Parse the storage bucket URL from the download URL
        return fileUrl.substringAfterLast("/")
    }
}

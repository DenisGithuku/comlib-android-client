
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
    open val url: String
        get() {
            return buildString {
                append(Constants.BASE_URL)
                append(path)
            }
        }

    sealed class Auth(
        route: String = "api/v1/users",
        param: String? = null,
        val path: String? = null
    ) : Endpoints(route + (param?.let { "/$it" } ?: "") + (path?.let { "/$it" } ?: "")) {
        data object Login : Auth(path = "/login")

        data object SignUp : Auth(path = "/signup")

        data object ResetPassword : Auth(path = "reset-password")
    }

    sealed class Books(route: String = "api/v1/books", private val param: String? = null) :
        Endpoints(route + (param?.let { "/$it" } ?: "")) {
        data class GetAll(val page: Int, val limit: Int) : Books() {
            override val url: String
                get() {
                    return buildString {
                        append(super.url)
                        append("?page=$page&limit=$limit")
                    }
                }
        }

        data object Add : Books()

        data class GetById(private val bookId: String) : Books(param = bookId)
    }

    sealed class Users(
        route: String = "api/v1/users",
        private val param: String? = null,
        val path: String? = null
    ) : Endpoints(route + (param?.let { "/$it" } ?: "") + (path?.let { "/$it" } ?: "")) {

        // Represents the route to get all read books
        data class GetReadBooks(val userId: String) : Users(param = userId, path = "read-books")

        // Represents the route to get a user by their ID
        data class GetById(val userId: String) : Users(param = userId)

        // Represents the route to complete_profile a user's data
        data class Update(val userId: String) : Users(param = userId)

        // Represents the route to delete a user by their ID
        data class Delete(val userId: String) : Users(param = userId)
    }

    data object Genres : Endpoints("api/v1/genres")

    data class Genre(private val id: String) : Endpoints("api/v1/genres/$id")
}

object FirebaseExt {
    fun getFilePathFromUrl(fileUrl: String): String {
        // Extract everything after "/o/" and before "?" (the storage path)
        val regex = Regex("/o/(.+?)\\?")
        val matchResult = regex.find(fileUrl)

        // Parse the storage bucket URL from the download URL
        return matchResult?.groupValues?.get(1)?.replace("%2F", "/")
            ?: throw IllegalArgumentException("Invalid file URL")
    }
}

package com.githukudenis.comlib.core.network.common

object Constants {
    const val BASE_URL =  "https://comlib-api.onrender.com/"
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
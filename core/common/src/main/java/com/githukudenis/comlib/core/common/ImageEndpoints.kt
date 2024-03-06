package com.githukudenis.comlib.core.common

sealed class ImageEndpoints(val path: String) {
    val url: String get() {
        return buildString {
            append("https://comlib-api.onrender.com/img/")
            append(path)
        }
    }

    data class Book(val imagePath: String): ImageEndpoints("books/$imagePath")
    data class User(val imagePath: String): ImageEndpoints("users/$imagePath")
}
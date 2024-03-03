package com.githukudenis.comlib.core.network.common

object Constants {
    const val BASE_URL =  "https://comlib-api.onrender.com/"
}

interface Endpoint {
    fun path(): String
}

object Books: Endpoint {
    override fun path(): String {
        return buildString {
            append(Constants.BASE_URL)
            append("api/v1/books")
        }
    }
}

object Users: Endpoint {
    override fun path(): String {
        return buildString {
            append(Constants.BASE_URL)
            append("api/v1/users")
        }
    }
}

object Genres: Endpoint {
    override fun path(): String {
        return buildString {
            append(Constants.BASE_URL)
            append("api/v1/genres")
        }
    }
}
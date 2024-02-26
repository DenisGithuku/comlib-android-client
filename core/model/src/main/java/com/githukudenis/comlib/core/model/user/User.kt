package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val _id: String? = null,
    val clubs: List<String> = emptyList(),
    val currentBooks: List<String> = emptyList(),
    val preferredGenres: List<String> = emptyList(),
    val email: String? = null,
    val authId: String? = null,
    val firstname: String? = null,
    val id: String? = null,
    val image: String? = null,
    val lastname: String? = null,
    val ownedBooks: List<String> = emptyList(),
    val username: String? = null
)
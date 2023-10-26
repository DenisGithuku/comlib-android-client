package com.githukudenis.comlib.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val _id: String? = null,
    val age: Int,
    val clubs: List<Club> = emptyList(),
    val currentBooks: List<String> = emptyList(),
    val email: String,
    val firstname: String,
    val id: String? = null,
    val image: String? = null,
    val lastname: String,
    val ownedBooks: List<String> = emptyList(),
    val username: String? = null
)
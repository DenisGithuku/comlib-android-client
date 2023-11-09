package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val _id: String,
    val clubs: List<String>,
    val currentBooks: List<String>,
    val id: String,
    val ownedBooks: List<String>,
    val username: String
)
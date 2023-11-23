package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val _id: String,
    val authors: List<String>,
    val currentHolder: String,
    val description: String,
    val edition: String,
    val genre_ids: List<String>,
    val id: String,
    val image: String,
    val owner: String,
    val pages: Int,
    val reserved: List<String> = emptyList(),
    val title: String
)
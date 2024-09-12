
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
package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val _id: String = "",
    val authors: List<String>,
    val currentHolder: String = "",
    val description: String,
    val edition: String,
    val genre_ids: List<String>,
    val id: String = "",
    val image: String = "",
    val owner: String,
    val pages: Int,
    val reserved: List<String> = emptyList(),
    val title: String
)

fun Book.toBookDTO(): BookDTO {
    return BookDTO(
        authors = authors.joinToString(","),
        description = description,
        edition = edition,
        genre_ids = genre_ids.joinToString(","),
        owner = owner,
        pages = pages,
        title = title,
        reserved = reserved.joinToString(","),
        _id = _id,
        currentHolder = currentHolder,
        id = id,
        image = image
    )
}

@Serializable
data class BookDTO(
    val _id: String = "",
    val authors: String,
    val currentHolder: String = "",
    val description: String,
    val edition: String,
    val genre_ids: String,
    val id: String = "",
    val image: String = "",
    val owner: String,
    val pages: Int,
    val reserved: String = "",
    val title: String = ""
)

fun BookDTO.toBook(): Book {
    return Book(
        _id = _id,
        authors = authors.split(','),
        currentHolder = currentHolder,
        description = description,
        edition = edition,
        genre_ids = genre_ids.split(','),
        id = id,
        image = image,
        owner = owner,
        pages = pages,
        reserved = reserved.split(','),
        title = title
    )
}

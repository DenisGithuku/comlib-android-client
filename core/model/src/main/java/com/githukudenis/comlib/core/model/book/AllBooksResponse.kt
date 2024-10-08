
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
data class AllBooksResponse(
    val data: BooksData,
    val requestedAt: String,
    val results: Int,
    val status: String
)

fun AllBooksResponse.asBooksByUserResponse(userId: String) =
    BooksByUserResponse(
        data = data.copy(books = data.books.filter { it.owner == userId }),
        requestedAt = requestedAt,
        results = results,
        status = status
    )

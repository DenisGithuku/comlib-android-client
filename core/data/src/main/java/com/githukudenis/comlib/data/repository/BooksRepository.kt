
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
package com.githukudenis.comlib.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.book.AddBookResponse
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.BookDTO
import com.githukudenis.comlib.core.model.book.BooksByUserResponse
import com.githukudenis.comlib.core.model.book.SingleBookResponse

interface BooksRepository {
    suspend fun getAllBooks(): ResponseResult<AllBooksResponse>

    suspend fun getBookById(id: String): ResponseResult<SingleBookResponse>

    suspend fun addNewBook(imageUri: Uri, book: BookDTO): ResponseResult<AddBookResponse>

    suspend fun getBooksByUser(userId: String): ResponseResult<BooksByUserResponse>
}

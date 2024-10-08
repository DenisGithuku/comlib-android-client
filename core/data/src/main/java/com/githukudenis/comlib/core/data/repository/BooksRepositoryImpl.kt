
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
package com.githukudenis.comlib.core.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.book.AddBookResponse
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.BookDTO
import com.githukudenis.comlib.core.model.book.BooksByUserResponse
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.model.book.asBooksByUserResponse
import com.githukudenis.comlib.core.network.BooksApi
import com.githukudenis.comlib.core.network.ImagesRemoteDataSource
import com.githukudenis.comlib.core.network.common.ImageStorageRef
import javax.inject.Inject
import kotlinx.coroutines.withContext

class BooksRepositoryImpl
@Inject
constructor(
    private val booksApi: BooksApi,
    private val dispatchers: ComlibCoroutineDispatchers,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) : BooksRepository {
    override suspend fun getAllBooks(): ResponseResult<AllBooksResponse> {
        return withContext(dispatchers.io) { booksApi.getBooks() }
    }

    override suspend fun getBookById(id: String): ResponseResult<SingleBookResponse> {
        return withContext(dispatchers.io) { booksApi.getBookById(id) }
    }

    override suspend fun addNewBook(imageUri: Uri, book: BookDTO): ResponseResult<AddBookResponse> {
        return withContext(dispatchers.io) {
            val imagePath = ImageStorageRef.Books(imageUri.lastPathSegment ?: "").ref

            val result = imagesRemoteDataSource.addImage(imageUri, imagePath)
            if (result.isSuccess) {
                booksApi.addNewBook(book)
            } else {
                ResponseResult.Failure(
                    ErrorResponse(
                        status = "fail",
                        message = result.exceptionOrNull()?.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    override suspend fun getBooksByUser(userId: String): ResponseResult<BooksByUserResponse> {
        return withContext(dispatchers.io) {
            when (val result = booksApi.getBooks()) {
                is ResponseResult.Failure -> {
                    ResponseResult.Failure(result.error)
                }
                is ResponseResult.Success -> {
                    ResponseResult.Success(data = result.data.asBooksByUserResponse(userId))
                }
            }
        }
    }
}

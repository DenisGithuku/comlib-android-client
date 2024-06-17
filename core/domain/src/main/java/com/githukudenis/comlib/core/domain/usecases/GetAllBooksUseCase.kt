
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
package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.dataResultSafeApiCall
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.data.repository.BooksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllBooksUseCase @Inject constructor(private val booksRepository: BooksRepository) {
    operator fun invoke(): Flow<DataResult<List<Book>>> = flow {
        val result = dataResultSafeApiCall {
            val books = booksRepository.getAllBooks()
            books.data.books
        }
        emit(result)
    }
}

package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.dataResultSafeApiCall
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(): Flow<DataResult<List<Book>>> = flow {
        val result = dataResultSafeApiCall {
            val books = booksRepository.getAllBooks()
            books.data.books
        }
        emit(result)
    }
}
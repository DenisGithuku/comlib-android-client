package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    suspend operator fun invoke(bookId: String): Flow<DataResult<Book>> = flow {
        try {
            emit(DataResult.Loading(null))
            val book = booksRepository.getBookById(bookId).data.book
            emit(DataResult.Success(data = book))
        } catch (e: Exception) {
            emit(DataResult.Error(message = "Could not fetch book", t = e))
        }
    }
}

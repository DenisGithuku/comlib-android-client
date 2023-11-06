package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(): Flow<DataResult<List<Book>>> = flow {
        try {
            emit(DataResult.Loading(data = null))
            val books = booksRepository.getAllBooks()
            emit(DataResult.Success(books.data.books))
        } catch (e: Exception) {
            Timber.e(e)
            if (e is CancellationException) throw e
            emit(DataResult.Error(message = e.message ?: "An unknown error occurred", t = e))
        }
    }
}
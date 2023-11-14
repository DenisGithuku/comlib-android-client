package com.githukudenis.comlib.core.domain.usecases

import android.util.Log
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.dataResultSafeApiCall
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
        val result = dataResultSafeApiCall {
            val books = booksRepository.getAllBooks()
            books.data.books
        }
        emit(result)
    }
}
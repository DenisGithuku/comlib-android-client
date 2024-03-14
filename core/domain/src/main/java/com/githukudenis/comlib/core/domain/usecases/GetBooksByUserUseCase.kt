package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.dataResultSafeApiCall
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.data.repository.BooksRepository
import javax.inject.Inject

class GetBooksByUserUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {
    suspend operator fun invoke(userId: String): DataResult<List<Book>> {
        return dataResultSafeApiCall {
            booksRepository.getAllBooks().data.books.filter { book -> book.owner == userId }
        }
    }
}
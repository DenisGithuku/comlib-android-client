package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.BooksRepository
import javax.inject.Inject

class GetGenreByIdUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {
    suspend operator fun invoke(genreId: String): Genre? {
        return try {
            val genre = booksRepository.getGenreById(genreId).data.genre
            genre
        } catch (e: Exception) {
            null
        }
    }
}
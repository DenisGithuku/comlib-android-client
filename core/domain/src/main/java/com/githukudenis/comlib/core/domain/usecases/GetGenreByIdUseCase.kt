package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.GenresRepository
import javax.inject.Inject

class GetGenreByIdUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke(genreId: String): Genre? {
        return try {
            val genre = genresRepository.getGenreById(genreId).data.genre
            genre
        } catch (e: Exception) {
            null
        }
    }
}
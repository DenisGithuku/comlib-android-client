package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val booksRepository: BooksRepository
){
    operator fun invoke(): Flow<List<Genre>> = flow {
        try {
            val genres = booksRepository.getGenres().data.genres
            emit(genres)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
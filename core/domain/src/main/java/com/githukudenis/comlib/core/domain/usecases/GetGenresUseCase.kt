package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val booksRepository: BooksRepository
){
    operator fun invoke(): Flow<DataResult<List<Genre>>> = flow {
        try {
            emit(DataResult.Loading(data = null))
            val genres = booksRepository.getGenres().data.genres
            emit(DataResult.Success(genres))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(DataResult.Error(e.localizedMessage ?: "An unexpected error occurred", t = e))
        }
    }
}
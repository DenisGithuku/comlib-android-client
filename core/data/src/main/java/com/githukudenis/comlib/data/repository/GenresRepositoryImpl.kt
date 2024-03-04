package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.GenresRemoteDataSource
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genresRemoteDataSource: GenresRemoteDataSource
): GenresRepository {
    override suspend fun getGenres(): AllGenresResponse {
        return genresRemoteDataSource.getGenres()
    }

    override suspend fun getGenreById(id: String): SingleGenreResponse {
        return genresRemoteDataSource.getGenreById(id)
    }
}
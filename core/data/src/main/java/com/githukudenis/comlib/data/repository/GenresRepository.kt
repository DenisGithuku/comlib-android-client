package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse

interface GenresRepository {
    suspend fun getGenres(): AllGenresResponse
    suspend fun getGenreById(id: String): SingleGenreResponse
}
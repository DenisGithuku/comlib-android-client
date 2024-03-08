package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.Data
import com.githukudenis.comlib.core.model.genre.DataX
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.data.repository.GenresRepository

class FakeGenresRepository: GenresRepository {
    val genres = (1..10).map {
        Genre(
            _id = "$it",
            id = "$it",
            name = "Genre$it"
        )
    }

    override suspend fun getGenres(): AllGenresResponse {
        return AllGenresResponse(
            data = DataX(genres),
            requestedAt = "now",
            results = genres.size,
            status = "Ok"
        )
    }

    override suspend fun getGenreById(id: String): SingleGenreResponse {
        return SingleGenreResponse(
            data = Data(genre = genres.first { it.id == id }),
            status = "Ok"
        )
    }
}
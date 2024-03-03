package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.genre.Genre

val genres = listOf(
    Genre(
        _id = "1", id = "1", name = "Fiction"
    ), Genre(
        _id = "2", id = "2", name = "Non-fiction"
    )
)

class FakeGenresRemoteDataSource {
    suspend fun getGenres() = genres

    suspend fun getGenreById(id: String) = genres.find { it.id == id }
}
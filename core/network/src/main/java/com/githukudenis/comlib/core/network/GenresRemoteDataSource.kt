package com.githukudenis.comlib.core.network

import javax.inject.Inject

class GenresRemoteDataSource @Inject constructor(
    private val genresApi: GenresApi
){
    suspend fun getGenres() = genresApi.getGenres()

    suspend fun getGenreById(id: String) = genresApi.getGenreById(id)
}
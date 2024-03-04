package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresApi @Inject constructor(
    private val httpClient: HttpClient,
    private val dispatchers: ComlibCoroutineDispatchers
) {
    suspend fun getGenres(): AllGenresResponse {
        return withContext(dispatchers.io) {
            httpClient.get<AllGenresResponse>(Endpoints.Genres.url)
        }
    }

    suspend fun getGenreById(genreId: String): SingleGenreResponse {
        return withContext(dispatchers.io) {
            httpClient.get<SingleGenreResponse>(Endpoints.Genre(genreId).url)
        }
    }
}
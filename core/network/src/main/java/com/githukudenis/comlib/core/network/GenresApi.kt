
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject
import kotlinx.coroutines.withContext

class GenresApi
@Inject
constructor(
    private val httpClient: HttpClient,
    private val dispatchers: ComlibCoroutineDispatchers
) {
    suspend fun getGenres(): AllGenresResponse {
        return withContext(dispatchers.io) { httpClient.get<AllGenresResponse>(Endpoints.Genres.url) }
    }

    suspend fun getGenreById(genreId: String): SingleGenreResponse {
        return withContext(dispatchers.io) {
            httpClient.get<SingleGenreResponse>(Endpoints.Genre(genreId).url)
        }
    }
}


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

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.safeApiCall
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class GenresApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getGenres(): ResponseResult<AllGenresResponse> = safeApiCall {
        httpClient.get(Endpoints.Genres.url)
    }

    suspend fun getGenreById(genreId: String): ResponseResult<SingleGenreResponse> = safeApiCall {
        httpClient.get(Endpoints.Genre(genreId).url)
    }
}

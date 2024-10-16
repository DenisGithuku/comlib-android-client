
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
package com.githukudenis.comlib.core.data.repository.fake

import com.githukudenis.comlib.core.common.ErrorResponse
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.GenresRepository
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.Data
import com.githukudenis.comlib.core.model.genre.DataX
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse

class FakeGenresRepository : GenresRepository {
    val genres = (1..10).map { Genre(_id = "$it", id = "$it", name = "Genre$it") }

    override suspend fun getGenres(): ResponseResult<AllGenresResponse> {
        return ResponseResult.Success(
            AllGenresResponse(
                data = DataX(genres),
                requestedAt = "now",
                results = genres.size,
                status = "Ok"
            )
        )
    }

    override suspend fun getGenreById(id: String): ResponseResult<SingleGenreResponse> {
        return try {
            ResponseResult.Success(
                SingleGenreResponse(data = Data(genre = genres.first { it.id == id }), status = "Ok")
            )
        } catch (e: Exception) {
            ResponseResult.Failure(
                ErrorResponse(message = e.message ?: "Unknown error", status = "error")
            )
        }
    }
}

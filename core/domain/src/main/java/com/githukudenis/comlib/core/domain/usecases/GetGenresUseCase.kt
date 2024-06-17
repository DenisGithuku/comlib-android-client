
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
package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.GenresRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGenresUseCase @Inject constructor(private val genresRepository: GenresRepository) {
    operator fun invoke(): Flow<DataResult<List<Genre>>> = flow {
        try {
            emit(DataResult.Loading(data = null))
            val genres = genresRepository.getGenres().data.genres
            emit(DataResult.Success(genres))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(DataResult.Error(e.localizedMessage ?: "An unexpected error occurred", t = e))
        }
    }
}

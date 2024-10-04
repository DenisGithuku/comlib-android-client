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
package com.githukudenis.comlib.feature.genre_setup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.GenresRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectableGenreItem(val genre: Genre, val isSelected: Boolean = false)

data class GenreSetupUiState(
    val isLoading: Boolean = false,
    val genres: List<SelectableGenreItem> = emptyList(),
    val isSetupComplete: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false
) {
    val screenIsValid: Boolean
        get() = genres.any { it.isSelected }
}

@HiltViewModel
class GenreSetupViewModel
@Inject constructor(
    private val genresRepository: GenresRepository,
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository
) : StatefulViewModel<GenreSetupUiState>(GenreSetupUiState()) {

    private var genresJob: Job? = null

    init {
        getGenres()
    }

    private fun getGenres() {
        update { copy(isLoading = true) }
        genresJob?.cancel()
        genresJob = viewModelScope.launch {
            when (val genres = genresRepository.getGenres()) {
                is ResponseResult.Failure -> {
                    update { copy(isLoading = false, error = genres.error.message) }
                }

                is ResponseResult.Success -> {
                    update {
                        copy(isLoading = false,
                            genres = genres.data.data.genres
                                .take(10)
                                .map { SelectableGenreItem(genre = it) })
                    }
                }
            }
        }
    }

    fun onRefresh() {
        getGenres()
    }

    fun onCompleteSetup() {
        viewModelScope.launch {
            update { copy(isSaving = true) }
            if (state.value.genres.any { it.isSelected }) {
                onUpdateUser()
            }
            userPrefsRepository.setSetupStatus(isComplete = true)
            update { copy(isSetupComplete = true, isSaving = false) }
        }
    }

    private suspend fun onUpdateUser() {
        val userId = userPrefsRepository.userPrefs.first().userId
        if (userId != null) {
            val result = userRepository.getUserById(
                userId
            )

            when (result) {
                is ResponseResult.Failure -> {
                    update { copy(isLoading = false, error = result.error.message) }
                }

                is ResponseResult.Success -> {
                    userRepository.updateUser(result.data.data.user.copy(preferredGenres = state.value.genres.filter { it.isSelected }
                        .map { it.genre.id }))
                }
            }
        }
    }

    fun onToggleGenreSelection(id: String) {
        val genres = state.value.genres.map {
            if (it.genre.id == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        update { copy(genres = genres) }
        Log.d("valid", "${state.value.screenIsValid}")
    }
}

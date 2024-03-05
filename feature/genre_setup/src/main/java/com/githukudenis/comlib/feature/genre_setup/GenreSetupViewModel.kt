package com.githukudenis.comlib.feature.genre_setup

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.genre.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectableGenreItem(
    val genre: Genre, val isSelected: Boolean = false
)

data class GenreSetupUiState(
    val isLoading: Boolean = false,
    val genres: List<SelectableGenreItem> = emptyList(),
    val isSetupComplete: Boolean = false,
    val error: String? = null
) {
    val screenIsValid: Boolean get() = genres.filter { it.isSelected }.size >= 3
}

@HiltViewModel
class GenreSetupViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases
) : StatefulViewModel<GenreSetupUiState>(GenreSetupUiState()) {

    private var genresJob: Job? = null

    init {
        getGenres()
    }

    private fun getGenres() {
        genresJob?.cancel()
        genresJob = viewModelScope.launch {
            comlibUseCases.getGenresUseCase().catch { throwable ->
                update {
                    copy(
                        isLoading = false,
                        error = throwable.message
                    )
                }
            }.collectLatest { result ->
                when (result) {
                    DataResult.Empty -> Unit
                    is DataResult.Error -> {
                        update { copy(isLoading = false, error = result.message) }
                    }

                    is DataResult.Loading -> update { copy(isLoading = true) }
                    is DataResult.Success -> {
                        update {
                            copy(
                                isLoading = false,
                                genres = result.data.map { SelectableGenreItem(genre = it) })
                        }
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
            if (state.value.genres.any { it.isSelected }) {
                onUpdateUser()
            }
            comlibUseCases.updateAppSetupState(isSetupComplete = true)
            update { copy(isSetupComplete = true) }
        }
    }

    private suspend fun onUpdateUser() {
        requireNotNull(comlibUseCases.getUserPrefsUseCase().first().userId).run {
            val user = comlibUseCases.getUserProfileUseCase(this)
            user?.let {
                val genres = it.preferredGenres.toMutableList()
                genres.addAll(state.value.genres.map { it.genre.id })
                comlibUseCases.updateUserUseCase(
                    it.copy(
                        preferredGenres = genres
                    )
                )
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
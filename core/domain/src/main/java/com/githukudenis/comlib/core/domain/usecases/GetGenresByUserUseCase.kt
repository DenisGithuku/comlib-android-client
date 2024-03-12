package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetGenresByUserUseCase @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        val genres = userPrefsRepository.userPrefs.mapLatest {
            it.preferredGenres
        }
        return genres
    }
}
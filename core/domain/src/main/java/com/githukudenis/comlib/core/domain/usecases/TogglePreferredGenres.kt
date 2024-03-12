package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.UserPrefsRepository
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class TogglePreferredGenres @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    suspend operator fun invoke(genres: Set<String>) {
        try {
            userPrefsRepository.setPreferredGenres(genres)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.e(e)
        }
    }
}
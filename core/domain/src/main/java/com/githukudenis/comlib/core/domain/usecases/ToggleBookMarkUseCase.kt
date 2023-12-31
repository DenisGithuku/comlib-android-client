package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

class ToggleBookMarkUseCase @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    suspend operator fun invoke(bookMarks: Set<String>) {
        try {
            userPrefsRepository.setBookMarks(bookMarks)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.e(e)
        }
    }
}
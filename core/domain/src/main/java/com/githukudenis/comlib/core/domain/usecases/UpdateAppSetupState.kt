package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.UserPrefsRepository
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class UpdateAppSetupState @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    suspend operator fun invoke(isSetupComplete: Boolean) {
        try {
            userPrefsRepository.setSetupStatus(isSetupComplete)
        }catch (e: Exception) {
            Timber.e(e)
            if (e is CancellationException) throw e
        }
    }
}
package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        try {
            authRepository.signOut()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
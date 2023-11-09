package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.UserPrefs
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPrefsUseCase @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    suspend operator fun invoke(): Flow<UserPrefs> {
        return userPrefsRepository.userPrefs
    }
}
package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetFavouriteBooksUseCase @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    suspend operator fun invoke(): Flow<Set<String>> =
        userPrefsRepository.userPrefs.mapLatest { prefs ->
            prefs.bookmarkedBooks
        }
}
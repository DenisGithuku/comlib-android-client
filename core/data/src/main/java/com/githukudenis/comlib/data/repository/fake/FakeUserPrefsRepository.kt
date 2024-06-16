package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserPrefsRepository : UserPrefsRepository {

    private var prefs = UserPrefs(
        authId = "owner@5",
        readBooks = setOf("1", "2", "3"),
        themeConfig = ThemeConfig.SYSTEM,
        bookmarkedBooks = setOf("1", "2", "3"),
        isSetup = false,
        preferredGenres = setOf("genre1", "genre2")
    )


    override val userPrefs: Flow<UserPrefs>
        get() = flowOf(prefs)

    override suspend fun setUserId(userId: String) {
        prefs = prefs.copy(
            authId = userId
        )
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        prefs = prefs.copy(themeConfig = themeConfig)
    }

    override suspend fun setBookMarks(bookMarks: Set<String>) {
        prefs = prefs.copy(bookmarkedBooks = bookMarks)
    }

    override suspend fun setSetupStatus(isComplete: Boolean) {
        prefs = prefs.copy(isSetup = isComplete)
    }

    override suspend fun setPreferredGenres(genres: Set<String>) {
        prefs = prefs.copy(preferredGenres = genres)
    }
}
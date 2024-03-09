package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserPrefsRepository: UserPrefsRepository {

    private var prefs = UserPrefs(
            userId = "randomId",
            readBooks = setOf("1", "2", "3"),
            themeConfig = ThemeConfig.SYSTEM,
            bookmarkedBooks = setOf("1", "2", "3"),
            isSetup = false
        )


    override val userPrefs: Flow<UserPrefs>
        get() = flowOf(prefs)

    override suspend fun setUserId(userId: String) {
        prefs = prefs.copy(
            userId = userId
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
}
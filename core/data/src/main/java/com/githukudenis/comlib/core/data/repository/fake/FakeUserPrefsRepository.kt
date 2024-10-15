
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.data.repository.fake

import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserPrefsRepository : UserPrefsRepository {

    private var _prefs =
        MutableStateFlow(
            UserPrefs(
                readBooks = setOf("1", "2", "3"),
                themeConfig = ThemeConfig.SYSTEM,
                bookmarkedBooks = setOf("1", "2", "3"),
                isSetup = false,
                preferredGenres = setOf("genre1", "genre2"),
                token = "token",
                userId = "owner@5",
                isNotificationsEnabled = true
            )
        )

    override val userPrefs: Flow<UserPrefs>
        get() = _prefs

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        _prefs.value = _prefs.value.copy(themeConfig = themeConfig)
    }

    override suspend fun setBookMarks(bookMarks: Set<String>) {
        _prefs.value = _prefs.value.copy(bookmarkedBooks = bookMarks)
    }

    override suspend fun setSetupStatus(isComplete: Boolean) {
        _prefs.value = _prefs.value.copy(isSetup = isComplete)
    }

    override suspend fun setPreferredGenres(genres: Set<String>) {
        _prefs.value = _prefs.value.copy(preferredGenres = genres)
    }

    override suspend fun clearSession() {
        _prefs.value = _prefs.value.copy(token = null, themeConfig = ThemeConfig.SYSTEM)
    }

    override suspend fun setToken(token: String) {
        _prefs.value = _prefs.value.copy(token = token)
    }

    override suspend fun setUserId(userId: String) {
        _prefs.value = _prefs.value.copy(userId = userId)
    }

    override suspend fun toggleNotifications(isToggled: Boolean) {
        _prefs.value = _prefs.value.copy(isNotificationsEnabled = isToggled)
    }
}

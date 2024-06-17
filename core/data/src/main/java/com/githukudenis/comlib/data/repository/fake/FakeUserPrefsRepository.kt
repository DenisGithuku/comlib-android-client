
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
package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserPrefsRepository : UserPrefsRepository {

    private var prefs =
        UserPrefs(
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
        prefs = prefs.copy(authId = userId)
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

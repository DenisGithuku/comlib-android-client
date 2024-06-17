
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
package com.githukudenis.comlib.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.ThemeConfigConverter
import com.githukudenis.comlib.core.model.UserPrefs
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPrefsDatasource @Inject constructor(private val prefsDataStore: DataStore<Preferences>) {
    val userPrefs: Flow<UserPrefs> =
        prefsDataStore.data.map { prefs ->
            UserPrefs(
                themeConfig =
                    ThemeConfigConverter.toThemeConfig(
                        prefs[PreferenceKeys.themeConfigPreferenceKey] ?: ThemeConfig.SYSTEM.name
                    ),
                authId = prefs[PreferenceKeys.userIdPreferenceKey],
                readBooks = prefs[PreferenceKeys.readBooks]?.toSet() ?: emptySet(),
                bookmarkedBooks = prefs[PreferenceKeys.bookmarkedBooks]?.toSet() ?: emptySet(),
                isSetup = prefs[PreferenceKeys.isSetup] ?: false,
                preferredGenres = prefs[PreferenceKeys.preferredGenres] ?: emptySet()
            )
        }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.themeConfigPreferenceKey] =
                ThemeConfigConverter.fromThemeConfig(themeConfig)
        }
    }

    suspend fun setUserId(userId: String) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.userIdPreferenceKey] = userId }
    }

    suspend fun setReadBooks(readBooks: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.readBooks] = readBooks }
    }

    suspend fun setBookmarkedBooks(bookmarkedBooks: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.bookmarkedBooks] = bookmarkedBooks }
    }

    suspend fun setSetupState(isComplete: Boolean) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.isSetup] = isComplete }
    }

    suspend fun setPreferredGenres(genres: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.preferredGenres] = genres }
    }
}

object PreferenceKeys {
    val themeConfigPreferenceKey = stringPreferencesKey("themeConfig")
    val userIdPreferenceKey = stringPreferencesKey("userId")
    val readBooks = stringSetPreferencesKey("readBooks")
    val bookmarkedBooks = stringSetPreferencesKey("bookmarkedBooks")
    val isSetup = booleanPreferencesKey("issetup")
    val preferredGenres = stringSetPreferencesKey("preferredGenres")
}

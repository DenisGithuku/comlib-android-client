package com.githukudenis.comlib.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.ThemeConfigConverter
import com.githukudenis.comlib.core.model.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPrefsDatasource @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
) {
    val userPrefs: Flow<UserPrefs> = prefsDataStore.data.map { prefs ->
        UserPrefs(
            themeConfig = ThemeConfigConverter.toThemeConfig(
                prefs[PreferenceKeys.themeConfigPreferenceKey] ?: ThemeConfig.SYSTEM.name
            ),
            userId = prefs[PreferenceKeys.userIdPreferenceKey],
            readBooks = prefs[PreferenceKeys.readBooks]?.toSet() ?: emptySet(),
            bookmarkedBooks = prefs[PreferenceKeys.bookmarkedBooks]?.toSet() ?: emptySet()
        )
    }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.themeConfigPreferenceKey] =
                ThemeConfigConverter.fromThemeConfig(themeConfig)
        }
    }

    suspend fun setUserId(userId: String) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.userIdPreferenceKey] = userId
        }
    }

    suspend fun setReadBooks(readBooks: Set<String>) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.readBooks] = readBooks.toSet()
        }
    }

    suspend fun setBookmarkedBooks(bookmarkedBooks: Set<String>) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.bookmarkedBooks] = bookmarkedBooks.toSet()
        }
    }

}

object PreferenceKeys {
    val themeConfigPreferenceKey = stringPreferencesKey("themeConfig")
    val userIdPreferenceKey = stringPreferencesKey("userId")
    val readBooks = stringSetPreferencesKey("readBooks")
    val bookmarkedBooks = stringSetPreferencesKey("bookmarkedBooks")
}
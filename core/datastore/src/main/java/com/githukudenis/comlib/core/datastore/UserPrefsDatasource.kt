
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
import com.githukudenis.comlib.core.model.UserProfileData
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
                token = prefs[PreferenceKeys.tokenPreferenceKey],
                readBooks = prefs[PreferenceKeys.readBooks]?.toSet() ?: emptySet(),
                bookmarkedBooks = prefs[PreferenceKeys.bookmarkedBooks]?.toSet() ?: emptySet(),
                reservedBooks = prefs[PreferenceKeys.reservedBooks]?.toSet() ?: emptySet(),
                isSetup = prefs[PreferenceKeys.isSetup] ?: false,
                preferredGenres = prefs[PreferenceKeys.preferredGenres] ?: emptySet(),
                userId = prefs[PreferenceKeys.userIdPrefsKey],
                isNotificationsEnabled = prefs[PreferenceKeys.isNotificationsEnabled] ?: false,
                userProfileData =
                    UserProfileData(
                        username = prefs[PreferenceKeys.userDataUsernameKey],
                        firstname = prefs[PreferenceKeys.userDataFirstnameKey],
                        lastname = prefs[PreferenceKeys.userDataLastnameKey],
                        email = prefs[PreferenceKeys.userDataEmailKey],
                        profilePicturePath = prefs[PreferenceKeys.userDataProfilePictureKey]
                    )
            )
        }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.themeConfigPreferenceKey] =
                ThemeConfigConverter.fromThemeConfig(themeConfig)
        }
    }

    suspend fun setToken(token: String) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.tokenPreferenceKey] = token }
    }

    suspend fun setReadBooks(readBooks: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.readBooks] = readBooks }
    }

    suspend fun setBookmarkedBooks(bookmarkedBooks: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.bookmarkedBooks] = bookmarkedBooks }
    }

    suspend fun setReservedBooks(reservedBooks: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.reservedBooks] = reservedBooks }
    }

    suspend fun setSetupState(isComplete: Boolean) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.isSetup] = isComplete }
    }

    suspend fun setPreferredGenres(genres: Set<String>) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.preferredGenres] = genres }
    }

    suspend fun clearSession() {
        prefsDataStore.edit { prefs ->
            prefs.remove(PreferenceKeys.tokenPreferenceKey)
            prefs.remove(PreferenceKeys.userIdPrefsKey)
            prefs.remove(PreferenceKeys.themeConfigPreferenceKey)
            prefs.remove(PreferenceKeys.readBooks)
            prefs.remove(PreferenceKeys.bookmarkedBooks)
            prefs.remove(PreferenceKeys.reservedBooks)
            prefs.remove(PreferenceKeys.preferredGenres)
            prefs.remove(PreferenceKeys.userDataUsernameKey)
            prefs.remove(PreferenceKeys.userDataFirstnameKey)
            prefs.remove(PreferenceKeys.userDataLastnameKey)
            prefs.remove(PreferenceKeys.userDataEmailKey)
            prefs.remove(PreferenceKeys.userDataProfilePictureKey)
        }
    }

    suspend fun setUserId(userId: String) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.userIdPrefsKey] = userId }
    }

    suspend fun toggleNotifications(isToggled: Boolean) {
        prefsDataStore.edit { prefs -> prefs[PreferenceKeys.isNotificationsEnabled] = isToggled }
    }

    suspend fun updateUserData(userData: UserProfileData) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.userDataUsernameKey] = userData.username ?: ""
            prefs[PreferenceKeys.userDataFirstnameKey] = userData.firstname ?: ""
            prefs[PreferenceKeys.userDataLastnameKey] = userData.lastname ?: ""
            prefs[PreferenceKeys.userDataEmailKey] = userData.email ?: ""
            prefs[PreferenceKeys.userDataProfilePictureKey] = userData.profilePicturePath ?: ""
        }
    }
}

object PreferenceKeys {
    val themeConfigPreferenceKey = stringPreferencesKey("themeConfig")
    val tokenPreferenceKey = stringPreferencesKey("token")
    val readBooks = stringSetPreferencesKey("readBooks")
    val bookmarkedBooks = stringSetPreferencesKey("bookmarkedBooks")
    val reservedBooks = stringSetPreferencesKey("reservedBooks")
    val isSetup = booleanPreferencesKey("issetup")
    val preferredGenres = stringSetPreferencesKey("preferredGenres")
    val userIdPrefsKey = stringPreferencesKey("userId")
    val isNotificationsEnabled = booleanPreferencesKey("isNotificationsEnabled")
    val userDataUsernameKey = stringPreferencesKey("userDataUsername")
    val userDataFirstnameKey = stringPreferencesKey("userDataFirstname")
    val userDataLastnameKey = stringPreferencesKey("userDataLastname")
    val userDataEmailKey = stringPreferencesKey("userDataEmail")
    val userDataProfilePictureKey = stringPreferencesKey("userDataProfilePicture")
}

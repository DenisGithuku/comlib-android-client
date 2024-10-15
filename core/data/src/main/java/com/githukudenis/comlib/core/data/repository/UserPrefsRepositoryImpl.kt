
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
package com.githukudenis.comlib.core.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.data.local.UserImageLocalHandler
import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import com.githukudenis.comlib.core.model.UserProfileData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserPrefsRepositoryImpl
@Inject
constructor(
    private val userPrefsDataSource: UserPrefsDatasource,
    private val userImageLocalHandler: UserImageLocalHandler
) : UserPrefsRepository {
    override val userPrefs: Flow<UserPrefs> = userPrefsDataSource.userPrefs

    override suspend fun setToken(token: String) {
        userPrefsDataSource.setToken(token)
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userPrefsDataSource.setThemeConfig(themeConfig)
    }

    override suspend fun setBookMarks(bookMarks: Set<String>) {
        userPrefsDataSource.setBookmarkedBooks(bookMarks)
    }

    override suspend fun setSetupStatus(isComplete: Boolean) {
        userPrefsDataSource.setSetupState(isComplete)
    }

    override suspend fun setPreferredGenres(genres: Set<String>) {
        userPrefsDataSource.setPreferredGenres(genres)
    }

    override suspend fun clearSession() {
        userPrefsDataSource.clearSession()
    }

    override suspend fun setUserId(userId: String) {
        userPrefsDataSource.setUserId(userId)
    }

    override suspend fun toggleNotifications(isToggled: Boolean) {
        userPrefsDataSource.toggleNotifications(isToggled)
    }

    override suspend fun setUserProfileData(userProfileData: UserProfileData) {
        userPrefsDataSource.updateUserData(userProfileData)
    }

    override suspend fun setProfilePicturePath(imageUri: Uri): String? {
        return userImageLocalHandler.saveImage(imageUri)
    }
}

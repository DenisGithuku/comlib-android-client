package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPrefsRepositoryImpl @Inject constructor(
    private val userPrefsDataSource: UserPrefsDatasource
): UserPrefsRepository {
    override val userPrefs: Flow<UserPrefs> = userPrefsDataSource.userPrefs

    override suspend fun setUserId(userId: String) {
        userPrefsDataSource.setUserId(userId)
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userPrefsDataSource.setThemeConfig(themeConfig)
    }
}
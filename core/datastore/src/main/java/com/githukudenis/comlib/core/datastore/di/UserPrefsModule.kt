package com.githukudenis.comlib.core.datastore.di

import android.content.Context
import android.preference.Preference
import android.preference.PreferenceDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.githukudenis.comlib.core.datastore.UserPrefsDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object UserPrefsModule {

    @Provides
    @Singleton
    fun provideDataStorePrefs(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(/* deserialize with a fallback strategy */
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = {
                emptyPreferences()
            }),/* list of migrations on how to move from the previous datastore */
            migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                context.preferencesDataStoreFile(USER_PREFERENCES)
            })
    }


    @Provides
    @Singleton
    fun provideUserDataSource(
        userPrefs: DataStore<Preferences>
    ): UserPrefsDatasource = UserPrefsDatasource(userPrefs)
}
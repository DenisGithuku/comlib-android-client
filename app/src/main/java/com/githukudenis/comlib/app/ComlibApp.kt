
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
package com.githukudenis.comlib.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.githukudenis.comlib.BuildConfig
import com.githukudenis.comlib.crashlytics.CrashlyticsTree
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class ComlibApp : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun initTimber() =
        when {
            BuildConfig.DEBUG -> {
                Timber.plant(
                    object : Timber.DebugTree() {
                        override fun createStackElementTag(element: StackTraceElement): String {
                            return super.createStackElementTag(element) + ":" + element.lineNumber
                        }
                    }
                )
            }
            else -> {
                Timber.plant(CrashlyticsTree())
            }
        }
}

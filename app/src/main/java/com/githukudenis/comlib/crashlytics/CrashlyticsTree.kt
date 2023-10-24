package com.githukudenis.comlib.crashlytics

import android.util.Log
import com.githukudenis.comlib.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTree: Timber.Tree() {

    private val crashlytics: FirebaseCrashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }

        if (BuildConfig.DEBUG) {
            crashlytics.setCrashlyticsCollectionEnabled(false)
            return
        }

        crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_PRIORITY, priority)
        if (tag != null) {
            crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_TAG, tag)
        }
        crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_MESSAGE, message)

        if (t == null) {
            crashlytics.recordException(Throwable(message))
        } else {
            crashlytics.recordException(t)
        }
    }
}

object CrashlyticsKeys {
    const val CRASHLYTICS_KEY_PRIORITY = "priority"
    const val CRASHLYTICS_KEY_TAG = "tag"
    const val CRASHLYTICS_KEY_MESSAGE = "message"
}
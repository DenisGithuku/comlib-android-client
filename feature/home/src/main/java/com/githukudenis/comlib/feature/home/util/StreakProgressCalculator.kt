package com.githukudenis.comlib.feature.home.util

import timber.log.Timber
import java.time.Instant
import java.time.ZoneId

class StreakProgressCalculator {
    operator fun invoke(startDate: Long, endDate: Long): Float? {
        return try {
            val startDateValue = Instant.ofEpochMilli(startDate)
                .atZone(ZoneId.systemDefault())
                .dayOfMonth

            val endDateValue = Instant.ofEpochMilli(endDate)
                .atZone(ZoneId.systemDefault())
                .dayOfMonth

            val today = Instant.now()
                .atZone(ZoneId.systemDefault())
                .dayOfMonth

            if(startDateValue >= today) {
                0f
            } else {
                ((endDateValue - today) / (endDateValue - startDateValue)).toFloat()
            }

        } catch (t: Throwable) {
            Timber.e(t)
            null
        }
    }
}
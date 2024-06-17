
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
package com.githukudenis.comlib.feature.home.util

import java.time.Instant
import java.time.ZoneId
import timber.log.Timber

class StreakProgressCalculator {
    operator fun invoke(startDate: Long, endDate: Long): Float? {
        return try {
            val startDateValue = Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).dayOfMonth

            val endDateValue = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).dayOfMonth

            val today = Instant.now().atZone(ZoneId.systemDefault()).dayOfMonth

            if (startDateValue >= today) {
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

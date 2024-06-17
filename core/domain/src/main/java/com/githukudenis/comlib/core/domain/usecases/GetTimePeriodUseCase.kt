
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
package com.githukudenis.comlib.core.domain.usecases

import java.util.Calendar

class GetTimePeriodUseCase {
    operator fun invoke(): TimePeriod {
        val timeNow = Calendar.getInstance()
        val timeString =
            when {
                timeNow.get(Calendar.HOUR_OF_DAY) < 12 -> TimePeriod.MORNING
                timeNow.get(Calendar.HOUR_OF_DAY) < 18 -> TimePeriod.AFTERNOON
                else -> TimePeriod.EVENING
            }
        return timeString
    }
}

enum class TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING
}

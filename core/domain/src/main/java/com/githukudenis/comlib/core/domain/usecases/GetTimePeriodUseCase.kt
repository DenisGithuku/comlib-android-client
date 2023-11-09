package com.githukudenis.comlib.core.domain.usecases

import java.util.Calendar

class GetTimePeriodUseCase {
    operator fun invoke(): TimePeriod {
        val timeNow = Calendar.getInstance()
        val timeString = when {
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
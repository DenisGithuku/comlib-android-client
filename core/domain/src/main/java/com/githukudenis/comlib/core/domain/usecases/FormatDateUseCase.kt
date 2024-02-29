package com.githukudenis.comlib.core.domain.usecases

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormatDateUseCase {
    fun invoke(dateLong: Long, pattern: String): String {
        return try {
            val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
            val date = Date(dateLong)
            val currentDate = Date()
            val formattedDate =
                if (dateFormatter.format(currentDate) == dateFormatter.format(date)) {
                    "Today"
                } else {
                    dateFormatter.format(date)
                }
            formattedDate
        } catch (e: Exception) {
            Timber.e(e)
            e.message.toString()
        }
    }
}
package com.githukudenis.comlib.core.domain.usecases

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormatDateUseCase {
    fun invoke(dateLong: Long): String {
        return try {
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
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
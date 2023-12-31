package com.githukudenis.comlib.core.model.book

data class BookMilestone(
    val bookId: String = "",
    val bookName: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L
)
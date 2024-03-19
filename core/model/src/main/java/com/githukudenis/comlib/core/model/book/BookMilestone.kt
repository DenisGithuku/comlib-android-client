package com.githukudenis.comlib.core.model.book

data class BookMilestone(
    val id: Long? = null,
    val bookId: String? = null,
    val bookName: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val pages: Int? = null
)
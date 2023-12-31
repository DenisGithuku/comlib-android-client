package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.book.BookMilestone
import kotlinx.coroutines.flow.Flow

interface BookMilestoneRepository {
    val bookMilestone: Flow<BookMilestone>

    suspend fun deleteBookMilestone(bookMilestone: BookMilestone)

    suspend fun updateBookMilestone(bookMilestone: BookMilestone)

    suspend fun insertBookMilestone(bookMilestone: BookMilestone)

}
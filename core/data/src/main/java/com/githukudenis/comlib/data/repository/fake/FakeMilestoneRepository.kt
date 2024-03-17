package com.githukudenis.comlib.data.repository.fake

import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMilestoneRepository: BookMilestoneRepository {
    private var currBookMilestone: BookMilestone? = BookMilestone(
        bookId = "randomId",
        bookName = "randomeBookName",
        startDate = 0L,
        endDate = 0L,
        pages = 100
    )
    override val bookMilestone: Flow<BookMilestone?>
        get() = flowOf(currBookMilestone)

    override suspend fun deleteBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone = null
    }

    override suspend fun updateBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone = currBookMilestone?.copy(
            bookId = bookMilestone.bookId,
            bookName = bookMilestone.bookName,
            startDate = bookMilestone.startDate,
            endDate = bookMilestone.endDate,
        )
    }

    override suspend fun insertBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone = bookMilestone
    }
}
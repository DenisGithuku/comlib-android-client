
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
package com.githukudenis.comlib.core.data.repository.fake

import com.githukudenis.comlib.core.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.core.model.book.BookMilestone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMilestoneRepository : BookMilestoneRepository {
    private var currBookMilestone: BookMilestone? =
        BookMilestone(bookId = "1", bookName = "testBook", startDate = 0L, endDate = 0L, pages = 100)
    override val bookMilestone: Flow<BookMilestone?>
        get() = flowOf(currBookMilestone)

    override suspend fun deleteBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone = null
    }

    override suspend fun updateBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone =
            currBookMilestone?.copy(
                bookId = bookMilestone.bookId,
                bookName = bookMilestone.bookName,
                startDate = bookMilestone.startDate,
                endDate = bookMilestone.endDate
            )
    }

    override suspend fun insertBookMilestone(bookMilestone: BookMilestone) {
        currBookMilestone = bookMilestone
    }
}

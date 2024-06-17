
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
package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.database.dao.BookMilestoneDao
import com.githukudenis.comlib.core.database.models.BookMilestoneEntity
import com.githukudenis.comlib.core.model.book.BookMilestone
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BookMilestoneRepositoryImpl
@Inject
constructor(
    private val bookMilestoneDao: BookMilestoneDao,
    private val dispatchers: ComlibCoroutineDispatchers
) : BookMilestoneRepository {
    override val bookMilestone: Flow<BookMilestone?>
        get() =
            flow {
                    val book = bookMilestoneDao.getMilestone()
                    if (book != null) {
                        emit(
                            BookMilestone(
                                id = book.id,
                                bookId = book.bookId,
                                bookName = book.bookName,
                                startDate = book.startDate,
                                endDate = book.endDate,
                                pages = book.pages
                            )
                        )
                    } else {
                        emit(null)
                    }
                }
                .flowOn(Dispatchers.IO)

    override suspend fun deleteBookMilestone(bookMilestone: BookMilestone) =
        withContext(dispatchers.io) {
            bookMilestoneDao.deleteMilestone(bookMilestone.asBookMilestoneEntity())
        }

    override suspend fun updateBookMilestone(bookMilestone: BookMilestone) =
        withContext(dispatchers.io) {
            bookMilestoneDao.updateMilestone(bookMilestone.asUpdatedMilestoneEntity())
        }

    override suspend fun insertBookMilestone(bookMilestone: BookMilestone) =
        withContext(dispatchers.io) {
            bookMilestoneDao.setMilestone(bookMilestone.asBookMilestoneEntity())
        }
}

fun BookMilestone.asBookMilestoneEntity(): BookMilestoneEntity {
    return BookMilestoneEntity(
        bookId = bookId,
        bookName = bookName,
        startDate = startDate,
        endDate = endDate,
        pages = pages
    )
}

fun BookMilestone.asUpdatedMilestoneEntity(): BookMilestoneEntity {
    return id?.let {
        BookMilestoneEntity(
            id = it,
            bookId = bookId,
            bookName = bookName,
            startDate = startDate,
            endDate = endDate,
            pages = pages
        )
    } ?: BookMilestoneEntity()
}

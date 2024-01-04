package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GetStreakUseCase @Inject constructor(
    private val bookMilestoneRepository: BookMilestoneRepository
){
        operator fun invoke(): Flow<BookMilestone?> {
            return try {
                bookMilestoneRepository.bookMilestone.map { it }
            } catch (e: Exception) {
                Timber.e(e)
                flowOf(null)
            }
        }
}
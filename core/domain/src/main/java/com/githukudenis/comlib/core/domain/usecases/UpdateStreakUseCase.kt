package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import timber.log.Timber
import javax.inject.Inject

class UpdateStreakUseCase @Inject constructor(
    private val bookMilestoneRepository: BookMilestoneRepository
){
    suspend operator fun invoke(bookMilestone: BookMilestone) {
        try {
            bookMilestoneRepository.updateBookMilestone(bookMilestone)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
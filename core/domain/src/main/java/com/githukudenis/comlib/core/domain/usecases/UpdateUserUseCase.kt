package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String, user: User) {
        try {
            userRepository.updateUser(id, user)
        } catch (e: Exception) {
            Timber.e(e)
            if (e is CancellationException) throw e
        }
    }
}
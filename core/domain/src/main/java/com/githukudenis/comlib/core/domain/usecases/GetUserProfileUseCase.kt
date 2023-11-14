package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): User? {
        return try {
            val result = userRepository.getUserById(userId)
            when (result) {
                is ResponseResult.Failure -> null
                is ResponseResult.Success -> result.data
            }
        } catch (e: Exception) {
            Timber.e(e)
            if (e is CancellationException) throw e
            null
        }
    }
}
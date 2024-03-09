package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.data.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(userAuthData: UserAuthData): ResponseResult<String?> {
        return authRepository.signUpWithEmail(userAuthData)
    }
}

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
package com.githukudenis.comlib.core.domain.usecases

import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject
import timber.log.Timber

class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String, user: User) {
        try {
            userRepository.updateUser(id, user)
        } catch (e: Exception) {
            Timber.e(e)
            if (e is CancellationException) throw e
        }
    }
}

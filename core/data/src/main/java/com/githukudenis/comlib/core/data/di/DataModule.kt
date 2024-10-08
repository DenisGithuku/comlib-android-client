
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
package com.githukudenis.comlib.core.data.di

import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.data.repository.AuthRepositoryImpl
import com.githukudenis.comlib.core.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.core.data.repository.BookMilestoneRepositoryImpl
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.BooksRepositoryImpl
import com.githukudenis.comlib.core.data.repository.GenresRepository
import com.githukudenis.comlib.core.data.repository.GenresRepositoryImpl
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepositoryImpl
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds fun bindBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): BooksRepository

    @Binds
    fun bindUserPrefsRepository(userPrefsRepositoryImpl: UserPrefsRepositoryImpl): UserPrefsRepository

    @Binds
    fun bindBookMilestoneRepository(
        bookMilestoneRepositoryImpl: BookMilestoneRepositoryImpl
    ): BookMilestoneRepository

    @Binds fun bindGenreRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository
}

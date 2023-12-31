package com.githukudenis.comlib.data.di

import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.AuthRepositoryImpl
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.BooksRepositoryImpl
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepositoryImpl
import com.githukudenis.comlib.data.repository.UserRepository
import com.githukudenis.comlib.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindBooksRepository(
        booksRepositoryImpl: BooksRepositoryImpl
    ): BooksRepository

    @Binds
    fun bindUserPrefsRepository(
        userPrefsRepositoryImpl: UserPrefsRepositoryImpl
    ): UserPrefsRepository
    @Binds
    fun bindBookMilestoneRepository(booksRepositoryImpl: BooksRepositoryImpl): BookMilestoneRepository
}
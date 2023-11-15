package com.githukudenis.comlib.core.domain.di

import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetFavouriteBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetTimePeriodUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUseCases(booksRepository: BooksRepository, userRepository: UserRepository, userPrefsRepository: UserPrefsRepository): ComlibUseCases {
        return ComlibUseCases(
            getAllBooksUseCase = GetAllBooksUseCase(booksRepository = booksRepository),
            getUserProfileUseCase = GetUserProfileUseCase(userRepository = userRepository),
            getUserPrefsUseCase = GetUserPrefsUseCase(userPrefsRepository = userPrefsRepository),
            getTimePeriodUseCase = GetTimePeriodUseCase(),
            getBookDetailsUseCase = GetBookDetailsUseCase(booksRepository = booksRepository),
            getFavouriteBooksUseCase = GetFavouriteBooksUseCase(userPrefsRepository = userPrefsRepository),
            getReadBooksUseCase = GetReadBooksUseCase(userPrefsRepository = userPrefsRepository)
        )
    }
}
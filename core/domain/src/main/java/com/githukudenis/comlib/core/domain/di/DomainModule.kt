package com.githukudenis.comlib.core.domain.di

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenresByUserUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase
import com.githukudenis.comlib.core.domain.usecases.GetNetworkConnectivityUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.GetTimePeriodUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase
import com.githukudenis.comlib.core.domain.usecases.SignUpUseCase
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.domain.usecases.TogglePreferredGenres
import com.githukudenis.comlib.core.domain.usecases.UpdateAppSetupState
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.GenresRepository
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
    fun provideFormatDateUseCase(): FormatDateUseCase = FormatDateUseCase()

    @Provides
    @Singleton
    fun provideGetAllBooksUseCase(booksRepository: BooksRepository): GetAllBooksUseCase = GetAllBooksUseCase(booksRepository)

    @Provides
    @Singleton
    fun provideGetBookDetailsUseCase(booksRepository: BooksRepository): GetBookDetailsUseCase = GetBookDetailsUseCase(booksRepository)

    @Provides
    @Singleton
    fun provideGetFavouriteBooksUseCase(userPrefsRepository: UserPrefsRepository): GetBookmarkedBooksUseCase = GetBookmarkedBooksUseCase(userPrefsRepository)

    @Provides
    @Singleton
    fun provideGetGenresUseCase(genresRepository: GenresRepository): GetGenresUseCase = GetGenresUseCase(genresRepository)

    @Provides
    @Singleton
    fun provideGetGenreUseCase(genresRepository: GenresRepository): GetGenreByIdUseCase = GetGenreByIdUseCase(genresRepository)

    @Singleton
    @Provides
    fun providesConnectivityUseCase(connectivityManager: ComlibConnectivityManager): GetNetworkConnectivityUseCase = GetNetworkConnectivityUseCase(connectivityManager)

    @Provides
    @Singleton
    fun provideGetReadBooksUseCase(userPrefsRepository: UserPrefsRepository): GetReadBooksUseCase = GetReadBooksUseCase(userPrefsRepository)

    @Provides
    @Singleton
    fun provideGetStreakUseCase(bookMilestoneRepository: BookMilestoneRepository): GetStreakUseCase = GetStreakUseCase(bookMilestoneRepository)

    @Provides
    @Singleton
    fun provideGetTimePeriodUseCase(): GetTimePeriodUseCase = GetTimePeriodUseCase()

    @Provides
    @Singleton
    fun provideGetUserPrefsUseCase(userPrefsRepository: UserPrefsRepository): GetUserPrefsUseCase = GetUserPrefsUseCase(userPrefsRepository)

    @Provides
    @Singleton
    fun providesGetUserProfileUseCase(userRepository: UserRepository): GetUserProfileUseCase = GetUserProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSaveStreakUseCase(bookMilestoneRepository: BookMilestoneRepository): SaveStreakUseCase = SaveStreakUseCase(bookMilestoneRepository)

    @Provides
    @Singleton
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase = SignOutUseCase(authRepository)

    @Provides
    @Singleton
    fun provideToggleBookMarkUseCase(userPrefsRepository: UserPrefsRepository): ToggleBookMarkUseCase = ToggleBookMarkUseCase(userPrefsRepository)

    @Provides
    @Singleton
    fun provideUpdateAppSetupState(userPrefsRepository: UserPrefsRepository): UpdateAppSetupState = UpdateAppSetupState(userPrefsRepository)

    @Provides
    @Singleton
    fun updateUserUseCase(userRepository: UserRepository): UpdateUserUseCase = UpdateUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase = SignUpUseCase(authRepository)

    @Provides
    @Singleton
    fun provideGetGenresByUserUseCase(userPrefsRepository: UserPrefsRepository): GetGenresByUserUseCase = GetGenresByUserUseCase(userPrefsRepository)

    @Provides
    @Singleton
    fun togglePreferredGenres(userPrefsRepository: UserPrefsRepository): TogglePreferredGenres = TogglePreferredGenres(userPrefsRepository)
}


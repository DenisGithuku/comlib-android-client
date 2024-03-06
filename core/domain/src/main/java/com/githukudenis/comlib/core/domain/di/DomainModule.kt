package com.githukudenis.comlib.core.domain.di

import com.githukudenis.comlib.core.common.ComlibConnectivityManager
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetFavouriteBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase
import com.githukudenis.comlib.core.domain.usecases.GetNetworkConnectivityUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.GetTimePeriodUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
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
    fun provideUseCases(booksRepository: BooksRepository, authRepository: AuthRepository, bookMilestoneRepository: BookMilestoneRepository, userRepository: UserRepository, userPrefsRepository: UserPrefsRepository, genresRepository: GenresRepository, comlibConnectivityManager: ComlibConnectivityManager): ComlibUseCases {
        return ComlibUseCases(
            getAllBooksUseCase = GetAllBooksUseCase(booksRepository = booksRepository),
            getUserProfileUseCase = GetUserProfileUseCase(userRepository = userRepository),
            getUserPrefsUseCase = GetUserPrefsUseCase(userPrefsRepository = userPrefsRepository),
            getTimePeriodUseCase = GetTimePeriodUseCase(),
            getBookDetailsUseCase = GetBookDetailsUseCase(booksRepository = booksRepository),
            getFavouriteBooksUseCase = GetFavouriteBooksUseCase(userPrefsRepository = userPrefsRepository),
            getReadBooksUseCase = GetReadBooksUseCase(userPrefsRepository = userPrefsRepository),
            getGenresUseCase = GetGenresUseCase(genresRepository = genresRepository),
            getGenreByIdUseCase = GetGenreByIdUseCase(genresRepository = genresRepository),
            signOutUseCase = SignOutUseCase(authRepository = authRepository),
            getNetworkConnectivityUseCase = GetNetworkConnectivityUseCase(comlibConnectivityManager = comlibConnectivityManager),
            toggleBookMarkUseCase = ToggleBookMarkUseCase(userPrefsRepository = userPrefsRepository),
            formatDateUseCase = FormatDateUseCase(),
            getStreakUseCase = GetStreakUseCase(bookMilestoneRepository = bookMilestoneRepository),
            saveStreakUseCase = SaveStreakUseCase(bookMilestoneRepository = bookMilestoneRepository),
            updateUserUseCase = UpdateUserUseCase(userRepository = userRepository),
            updateAppSetupState = UpdateAppSetupState(userPrefsRepository = userPrefsRepository)
        )
    }
}
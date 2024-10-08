package com.githukudenis.comlib.core.domain.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ba\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\b\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020*2\u0006\u0010\'\u001a\u00020(H\u0007J\u0010\u0010+\u001a\u00020,2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010-\u001a\u00020.2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010/\u001a\u0002002\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0007J\u0010\u00109\u001a\u00020:2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010;\u001a\u00020<2\u0006\u00107\u001a\u000208H\u0007\u00a8\u0006="}, d2 = {"Lcom/githukudenis/comlib/core/domain/di/DomainModule;", "", "()V", "provideFormatDateUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/FormatDateUseCase;", "provideGetAllBooksUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetAllBooksUseCase;", "booksRepository", "Lcom/githukudenis/comlib/data/repository/BooksRepository;", "provideGetBookDetailsUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetBookDetailsUseCase;", "provideGetBooksByUserUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetBooksByUserUseCase;", "provideGetFavouriteBooksUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetBookmarkedBooksUseCase;", "userPrefsRepository", "Lcom/githukudenis/comlib/data/repository/UserPrefsRepository;", "provideGetGenreUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetGenreByIdUseCase;", "genresRepository", "Lcom/githukudenis/comlib/data/repository/GenresRepository;", "provideGetGenresByUserUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetGenresByUserUseCase;", "provideGetGenresUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetGenresUseCase;", "provideGetReadBooksUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetReadBooksUseCase;", "provideGetStreakUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetStreakUseCase;", "bookMilestoneRepository", "Lcom/githukudenis/comlib/data/repository/BookMilestoneRepository;", "provideGetTimePeriodUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetTimePeriodUseCase;", "provideGetUserPrefsUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetUserPrefsUseCase;", "provideSaveStreakUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/SaveStreakUseCase;", "provideSignOutUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/SignOutUseCase;", "authRepository", "Lcom/githukudenis/comlib/data/repository/AuthRepository;", "provideSignUpUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/SignUpUseCase;", "provideToggleBookMarkUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/ToggleBookMarkUseCase;", "provideUpdateAppSetupState", "Lcom/githukudenis/comlib/core/domain/usecases/UpdateAppSetupState;", "provideUpdateStreakUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/UpdateStreakUseCase;", "providesConnectivityUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetNetworkConnectivityUseCase;", "connectivityManager", "Lcom/githukudenis/comlib/core/common/ComlibConnectivityManager;", "providesGetUserProfileUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/GetUserProfileUseCase;", "userRepository", "Lcom/githukudenis/comlib/data/repository/UserRepository;", "togglePreferredGenres", "Lcom/githukudenis/comlib/core/domain/usecases/TogglePreferredGenres;", "updateUserUseCase", "Lcom/githukudenis/comlib/core/domain/usecases/UpdateUserUseCase;", "domain_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DomainModule {
    @org.jetbrains.annotations.NotNull
    public static final com.githukudenis.comlib.core.domain.di.DomainModule INSTANCE = null;
    
    private DomainModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase provideFormatDateUseCase() {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase provideGetAllBooksUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BooksRepository booksRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase provideGetBookDetailsUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BooksRepository booksRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase provideGetFavouriteBooksUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase provideGetGenresUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.GenresRepository genresRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase provideGetGenreUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.GenresRepository genresRepository) {
        return null;
    }
    
    @javax.inject.Singleton
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetNetworkConnectivityUseCase providesConnectivityUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.common.ComlibConnectivityManager connectivityManager) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase provideGetReadBooksUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase provideGetStreakUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BookMilestoneRepository bookMilestoneRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetTimePeriodUseCase provideGetTimePeriodUseCase() {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase provideGetUserPrefsUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase providesGetUserProfileUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserRepository userRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase provideSaveStreakUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BookMilestoneRepository bookMilestoneRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.SignOutUseCase provideSignOutUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.AuthRepository authRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase provideToggleBookMarkUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.UpdateAppSetupState provideUpdateAppSetupState(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase updateUserUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserRepository userRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.SignUpUseCase provideSignUpUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.AuthRepository authRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetGenresByUserUseCase provideGetGenresByUserUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.TogglePreferredGenres togglePreferredGenres(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.UserPrefsRepository userPrefsRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.GetBooksByUserUseCase provideGetBooksByUserUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BooksRepository booksRepository) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.domain.usecases.UpdateStreakUseCase provideUpdateStreakUseCase(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.data.repository.BookMilestoneRepository bookMilestoneRepository) {
        return null;
    }
}
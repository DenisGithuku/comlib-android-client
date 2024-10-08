package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase;
import com.githukudenis.comlib.data.repository.UserPrefsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DomainModule_ProvideGetFavouriteBooksUseCaseFactory implements Factory<GetBookmarkedBooksUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideGetFavouriteBooksUseCaseFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetBookmarkedBooksUseCase get() {
    return provideGetFavouriteBooksUseCase(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetFavouriteBooksUseCaseFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideGetFavouriteBooksUseCaseFactory(userPrefsRepositoryProvider);
  }

  public static GetBookmarkedBooksUseCase provideGetFavouriteBooksUseCase(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetFavouriteBooksUseCase(userPrefsRepository));
  }
}

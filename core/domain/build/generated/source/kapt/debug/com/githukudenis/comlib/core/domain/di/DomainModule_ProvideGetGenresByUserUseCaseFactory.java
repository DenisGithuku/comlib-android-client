package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetGenresByUserUseCase;
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
public final class DomainModule_ProvideGetGenresByUserUseCaseFactory implements Factory<GetGenresByUserUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideGetGenresByUserUseCaseFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetGenresByUserUseCase get() {
    return provideGetGenresByUserUseCase(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetGenresByUserUseCaseFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideGetGenresByUserUseCaseFactory(userPrefsRepositoryProvider);
  }

  public static GetGenresByUserUseCase provideGetGenresByUserUseCase(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetGenresByUserUseCase(userPrefsRepository));
  }
}

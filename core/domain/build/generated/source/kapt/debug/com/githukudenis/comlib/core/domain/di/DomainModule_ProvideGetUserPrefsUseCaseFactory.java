package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase;
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
public final class DomainModule_ProvideGetUserPrefsUseCaseFactory implements Factory<GetUserPrefsUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideGetUserPrefsUseCaseFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetUserPrefsUseCase get() {
    return provideGetUserPrefsUseCase(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetUserPrefsUseCaseFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideGetUserPrefsUseCaseFactory(userPrefsRepositoryProvider);
  }

  public static GetUserPrefsUseCase provideGetUserPrefsUseCase(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetUserPrefsUseCase(userPrefsRepository));
  }
}

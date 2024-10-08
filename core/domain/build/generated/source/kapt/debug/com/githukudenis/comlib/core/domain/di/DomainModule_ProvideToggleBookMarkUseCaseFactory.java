package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase;
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
public final class DomainModule_ProvideToggleBookMarkUseCaseFactory implements Factory<ToggleBookMarkUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideToggleBookMarkUseCaseFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public ToggleBookMarkUseCase get() {
    return provideToggleBookMarkUseCase(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideToggleBookMarkUseCaseFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideToggleBookMarkUseCaseFactory(userPrefsRepositoryProvider);
  }

  public static ToggleBookMarkUseCase provideToggleBookMarkUseCase(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideToggleBookMarkUseCase(userPrefsRepository));
  }
}

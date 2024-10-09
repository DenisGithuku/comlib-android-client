package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase;
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
public final class DomainModule_ProvideGetReadBooksUseCaseFactory implements Factory<GetReadBooksUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_ProvideGetReadBooksUseCaseFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetReadBooksUseCase get() {
    return provideGetReadBooksUseCase(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetReadBooksUseCaseFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_ProvideGetReadBooksUseCaseFactory(userPrefsRepositoryProvider);
  }

  public static GetReadBooksUseCase provideGetReadBooksUseCase(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetReadBooksUseCase(userPrefsRepository));
  }
}

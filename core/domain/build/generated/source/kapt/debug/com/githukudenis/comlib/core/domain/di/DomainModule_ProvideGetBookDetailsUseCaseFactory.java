package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase;
import com.githukudenis.comlib.data.repository.BooksRepository;
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
public final class DomainModule_ProvideGetBookDetailsUseCaseFactory implements Factory<GetBookDetailsUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public DomainModule_ProvideGetBookDetailsUseCaseFactory(
      Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetBookDetailsUseCase get() {
    return provideGetBookDetailsUseCase(booksRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetBookDetailsUseCaseFactory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new DomainModule_ProvideGetBookDetailsUseCaseFactory(booksRepositoryProvider);
  }

  public static GetBookDetailsUseCase provideGetBookDetailsUseCase(
      BooksRepository booksRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetBookDetailsUseCase(booksRepository));
  }
}

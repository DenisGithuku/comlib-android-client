package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetBooksByUserUseCase;
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
public final class DomainModule_ProvideGetBooksByUserUseCaseFactory implements Factory<GetBooksByUserUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public DomainModule_ProvideGetBooksByUserUseCaseFactory(
      Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetBooksByUserUseCase get() {
    return provideGetBooksByUserUseCase(booksRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetBooksByUserUseCaseFactory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new DomainModule_ProvideGetBooksByUserUseCaseFactory(booksRepositoryProvider);
  }

  public static GetBooksByUserUseCase provideGetBooksByUserUseCase(
      BooksRepository booksRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetBooksByUserUseCase(booksRepository));
  }
}

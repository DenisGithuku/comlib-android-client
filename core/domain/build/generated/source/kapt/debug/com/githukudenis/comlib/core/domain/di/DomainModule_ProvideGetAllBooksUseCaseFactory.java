package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase;
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
public final class DomainModule_ProvideGetAllBooksUseCaseFactory implements Factory<GetAllBooksUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public DomainModule_ProvideGetAllBooksUseCaseFactory(
      Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetAllBooksUseCase get() {
    return provideGetAllBooksUseCase(booksRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetAllBooksUseCaseFactory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new DomainModule_ProvideGetAllBooksUseCaseFactory(booksRepositoryProvider);
  }

  public static GetAllBooksUseCase provideGetAllBooksUseCase(BooksRepository booksRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetAllBooksUseCase(booksRepository));
  }
}

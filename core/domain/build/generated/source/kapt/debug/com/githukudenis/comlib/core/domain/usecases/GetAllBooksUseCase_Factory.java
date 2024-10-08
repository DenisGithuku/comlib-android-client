package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.BooksRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class GetAllBooksUseCase_Factory implements Factory<GetAllBooksUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public GetAllBooksUseCase_Factory(Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetAllBooksUseCase get() {
    return newInstance(booksRepositoryProvider.get());
  }

  public static GetAllBooksUseCase_Factory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new GetAllBooksUseCase_Factory(booksRepositoryProvider);
  }

  public static GetAllBooksUseCase newInstance(BooksRepository booksRepository) {
    return new GetAllBooksUseCase(booksRepository);
  }
}

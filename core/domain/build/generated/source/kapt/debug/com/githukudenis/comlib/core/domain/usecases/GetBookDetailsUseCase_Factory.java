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
public final class GetBookDetailsUseCase_Factory implements Factory<GetBookDetailsUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public GetBookDetailsUseCase_Factory(Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetBookDetailsUseCase get() {
    return newInstance(booksRepositoryProvider.get());
  }

  public static GetBookDetailsUseCase_Factory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new GetBookDetailsUseCase_Factory(booksRepositoryProvider);
  }

  public static GetBookDetailsUseCase newInstance(BooksRepository booksRepository) {
    return new GetBookDetailsUseCase(booksRepository);
  }
}

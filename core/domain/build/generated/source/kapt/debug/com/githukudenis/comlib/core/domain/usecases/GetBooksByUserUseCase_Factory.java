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
public final class GetBooksByUserUseCase_Factory implements Factory<GetBooksByUserUseCase> {
  private final Provider<BooksRepository> booksRepositoryProvider;

  public GetBooksByUserUseCase_Factory(Provider<BooksRepository> booksRepositoryProvider) {
    this.booksRepositoryProvider = booksRepositoryProvider;
  }

  @Override
  public GetBooksByUserUseCase get() {
    return newInstance(booksRepositoryProvider.get());
  }

  public static GetBooksByUserUseCase_Factory create(
      Provider<BooksRepository> booksRepositoryProvider) {
    return new GetBooksByUserUseCase_Factory(booksRepositoryProvider);
  }

  public static GetBooksByUserUseCase newInstance(BooksRepository booksRepository) {
    return new GetBooksByUserUseCase(booksRepository);
  }
}

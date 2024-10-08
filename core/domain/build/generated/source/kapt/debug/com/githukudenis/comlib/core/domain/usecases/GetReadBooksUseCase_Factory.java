package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.UserPrefsRepository;
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
public final class GetReadBooksUseCase_Factory implements Factory<GetReadBooksUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public GetReadBooksUseCase_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetReadBooksUseCase get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static GetReadBooksUseCase_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new GetReadBooksUseCase_Factory(userPrefsRepositoryProvider);
  }

  public static GetReadBooksUseCase newInstance(UserPrefsRepository userPrefsRepository) {
    return new GetReadBooksUseCase(userPrefsRepository);
  }
}

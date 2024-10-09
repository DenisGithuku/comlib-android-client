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
public final class GetBookmarkedBooksUseCase_Factory implements Factory<GetBookmarkedBooksUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public GetBookmarkedBooksUseCase_Factory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetBookmarkedBooksUseCase get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static GetBookmarkedBooksUseCase_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new GetBookmarkedBooksUseCase_Factory(userPrefsRepositoryProvider);
  }

  public static GetBookmarkedBooksUseCase newInstance(UserPrefsRepository userPrefsRepository) {
    return new GetBookmarkedBooksUseCase(userPrefsRepository);
  }
}

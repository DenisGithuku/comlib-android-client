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
public final class GetGenresByUserUseCase_Factory implements Factory<GetGenresByUserUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public GetGenresByUserUseCase_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetGenresByUserUseCase get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static GetGenresByUserUseCase_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new GetGenresByUserUseCase_Factory(userPrefsRepositoryProvider);
  }

  public static GetGenresByUserUseCase newInstance(UserPrefsRepository userPrefsRepository) {
    return new GetGenresByUserUseCase(userPrefsRepository);
  }
}
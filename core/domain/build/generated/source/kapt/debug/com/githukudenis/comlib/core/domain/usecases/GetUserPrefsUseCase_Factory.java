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
public final class GetUserPrefsUseCase_Factory implements Factory<GetUserPrefsUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public GetUserPrefsUseCase_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public GetUserPrefsUseCase get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static GetUserPrefsUseCase_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new GetUserPrefsUseCase_Factory(userPrefsRepositoryProvider);
  }

  public static GetUserPrefsUseCase newInstance(UserPrefsRepository userPrefsRepository) {
    return new GetUserPrefsUseCase(userPrefsRepository);
  }
}

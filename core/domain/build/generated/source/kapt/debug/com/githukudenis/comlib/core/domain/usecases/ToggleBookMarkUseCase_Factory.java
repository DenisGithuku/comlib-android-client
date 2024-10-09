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
public final class ToggleBookMarkUseCase_Factory implements Factory<ToggleBookMarkUseCase> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public ToggleBookMarkUseCase_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public ToggleBookMarkUseCase get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static ToggleBookMarkUseCase_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new ToggleBookMarkUseCase_Factory(userPrefsRepositoryProvider);
  }

  public static ToggleBookMarkUseCase newInstance(UserPrefsRepository userPrefsRepository) {
    return new ToggleBookMarkUseCase(userPrefsRepository);
  }
}
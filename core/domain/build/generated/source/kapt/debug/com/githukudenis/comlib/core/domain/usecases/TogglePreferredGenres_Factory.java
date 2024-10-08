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
public final class TogglePreferredGenres_Factory implements Factory<TogglePreferredGenres> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public TogglePreferredGenres_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public TogglePreferredGenres get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static TogglePreferredGenres_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new TogglePreferredGenres_Factory(userPrefsRepositoryProvider);
  }

  public static TogglePreferredGenres newInstance(UserPrefsRepository userPrefsRepository) {
    return new TogglePreferredGenres(userPrefsRepository);
  }
}

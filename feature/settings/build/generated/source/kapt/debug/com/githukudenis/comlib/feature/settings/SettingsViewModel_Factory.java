package com.githukudenis.comlib.feature.settings;

import com.githukudenis.comlib.core.data.repository.UserPrefsRepository;
import com.githukudenis.comlib.core.data.repository.UserRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public SettingsViewModel_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(userPrefsRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new SettingsViewModel_Factory(userPrefsRepositoryProvider, userRepositoryProvider);
  }

  public static SettingsViewModel newInstance(UserPrefsRepository userPrefsRepository,
      UserRepository userRepository) {
    return new SettingsViewModel(userPrefsRepository, userRepository);
  }
}

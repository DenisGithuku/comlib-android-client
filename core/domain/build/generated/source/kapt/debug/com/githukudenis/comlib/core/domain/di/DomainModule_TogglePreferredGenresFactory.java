package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.TogglePreferredGenres;
import com.githukudenis.comlib.data.repository.UserPrefsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DomainModule_TogglePreferredGenresFactory implements Factory<TogglePreferredGenres> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public DomainModule_TogglePreferredGenresFactory(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public TogglePreferredGenres get() {
    return togglePreferredGenres(userPrefsRepositoryProvider.get());
  }

  public static DomainModule_TogglePreferredGenresFactory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new DomainModule_TogglePreferredGenresFactory(userPrefsRepositoryProvider);
  }

  public static TogglePreferredGenres togglePreferredGenres(
      UserPrefsRepository userPrefsRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.togglePreferredGenres(userPrefsRepository));
  }
}

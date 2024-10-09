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
public final class UpdateAppSetupState_Factory implements Factory<UpdateAppSetupState> {
  private final Provider<UserPrefsRepository> userPrefsRepositoryProvider;

  public UpdateAppSetupState_Factory(Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    this.userPrefsRepositoryProvider = userPrefsRepositoryProvider;
  }

  @Override
  public UpdateAppSetupState get() {
    return newInstance(userPrefsRepositoryProvider.get());
  }

  public static UpdateAppSetupState_Factory create(
      Provider<UserPrefsRepository> userPrefsRepositoryProvider) {
    return new UpdateAppSetupState_Factory(userPrefsRepositoryProvider);
  }

  public static UpdateAppSetupState newInstance(UserPrefsRepository userPrefsRepository) {
    return new UpdateAppSetupState(userPrefsRepository);
  }
}

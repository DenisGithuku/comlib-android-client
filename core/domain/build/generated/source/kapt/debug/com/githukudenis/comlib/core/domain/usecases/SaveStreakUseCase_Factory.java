package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.BookMilestoneRepository;
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
public final class SaveStreakUseCase_Factory implements Factory<SaveStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public SaveStreakUseCase_Factory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public SaveStreakUseCase get() {
    return newInstance(bookMilestoneRepositoryProvider.get());
  }

  public static SaveStreakUseCase_Factory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new SaveStreakUseCase_Factory(bookMilestoneRepositoryProvider);
  }

  public static SaveStreakUseCase newInstance(BookMilestoneRepository bookMilestoneRepository) {
    return new SaveStreakUseCase(bookMilestoneRepository);
  }
}

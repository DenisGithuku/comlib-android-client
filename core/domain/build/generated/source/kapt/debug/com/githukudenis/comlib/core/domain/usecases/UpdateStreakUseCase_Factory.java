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
public final class UpdateStreakUseCase_Factory implements Factory<UpdateStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public UpdateStreakUseCase_Factory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public UpdateStreakUseCase get() {
    return newInstance(bookMilestoneRepositoryProvider.get());
  }

  public static UpdateStreakUseCase_Factory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new UpdateStreakUseCase_Factory(bookMilestoneRepositoryProvider);
  }

  public static UpdateStreakUseCase newInstance(BookMilestoneRepository bookMilestoneRepository) {
    return new UpdateStreakUseCase(bookMilestoneRepository);
  }
}

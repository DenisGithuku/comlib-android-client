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
public final class GetStreakUseCase_Factory implements Factory<GetStreakUseCase> {
  private final Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider;

  public GetStreakUseCase_Factory(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    this.bookMilestoneRepositoryProvider = bookMilestoneRepositoryProvider;
  }

  @Override
  public GetStreakUseCase get() {
    return newInstance(bookMilestoneRepositoryProvider.get());
  }

  public static GetStreakUseCase_Factory create(
      Provider<BookMilestoneRepository> bookMilestoneRepositoryProvider) {
    return new GetStreakUseCase_Factory(bookMilestoneRepositoryProvider);
  }

  public static GetStreakUseCase newInstance(BookMilestoneRepository bookMilestoneRepository) {
    return new GetStreakUseCase(bookMilestoneRepository);
  }
}

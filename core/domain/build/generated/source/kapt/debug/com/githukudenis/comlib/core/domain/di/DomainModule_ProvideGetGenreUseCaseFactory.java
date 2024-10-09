package com.githukudenis.comlib.core.domain.di;

import com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase;
import com.githukudenis.comlib.data.repository.GenresRepository;
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
public final class DomainModule_ProvideGetGenreUseCaseFactory implements Factory<GetGenreByIdUseCase> {
  private final Provider<GenresRepository> genresRepositoryProvider;

  public DomainModule_ProvideGetGenreUseCaseFactory(
      Provider<GenresRepository> genresRepositoryProvider) {
    this.genresRepositoryProvider = genresRepositoryProvider;
  }

  @Override
  public GetGenreByIdUseCase get() {
    return provideGetGenreUseCase(genresRepositoryProvider.get());
  }

  public static DomainModule_ProvideGetGenreUseCaseFactory create(
      Provider<GenresRepository> genresRepositoryProvider) {
    return new DomainModule_ProvideGetGenreUseCaseFactory(genresRepositoryProvider);
  }

  public static GetGenreByIdUseCase provideGetGenreUseCase(GenresRepository genresRepository) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideGetGenreUseCase(genresRepository));
  }
}

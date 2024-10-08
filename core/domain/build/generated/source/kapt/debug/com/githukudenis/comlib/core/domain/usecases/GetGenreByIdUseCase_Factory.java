package com.githukudenis.comlib.core.domain.usecases;

import com.githukudenis.comlib.data.repository.GenresRepository;
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
public final class GetGenreByIdUseCase_Factory implements Factory<GetGenreByIdUseCase> {
  private final Provider<GenresRepository> genresRepositoryProvider;

  public GetGenreByIdUseCase_Factory(Provider<GenresRepository> genresRepositoryProvider) {
    this.genresRepositoryProvider = genresRepositoryProvider;
  }

  @Override
  public GetGenreByIdUseCase get() {
    return newInstance(genresRepositoryProvider.get());
  }

  public static GetGenreByIdUseCase_Factory create(
      Provider<GenresRepository> genresRepositoryProvider) {
    return new GetGenreByIdUseCase_Factory(genresRepositoryProvider);
  }

  public static GetGenreByIdUseCase newInstance(GenresRepository genresRepository) {
    return new GetGenreByIdUseCase(genresRepository);
  }
}

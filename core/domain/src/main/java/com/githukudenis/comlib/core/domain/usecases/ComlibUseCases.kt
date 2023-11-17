package com.githukudenis.comlib.core.domain.usecases

data class ComlibUseCases(
    val getAllBooksUseCase: GetAllBooksUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getUserPrefsUseCase: GetUserPrefsUseCase,
    val getTimePeriodUseCase: GetTimePeriodUseCase,
    val getBookDetailsUseCase: GetBookDetailsUseCase,
    val getFavouriteBooksUseCase: GetFavouriteBooksUseCase,
    val getReadBooksUseCase: GetReadBooksUseCase,
    val getGenresUseCase: GetGenresUseCase,
    val getGenreByIdUseCase: GetGenreByIdUseCase,
)

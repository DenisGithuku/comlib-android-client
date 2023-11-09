package com.githukudenis.comlib.core.domain.usecases

data class ComlibUseCases(
    val getAllBooksUseCase: GetAllBooksUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
    val getUserPrefsUseCase: GetUserPrefsUseCase,
    val getTimePeriodUseCase: GetTimePeriodUseCase,
)

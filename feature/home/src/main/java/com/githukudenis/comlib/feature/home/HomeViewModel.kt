package com.githukudenis.comlib.feature.home

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val reads: List<String> = emptyList(),
    val availableState: FetchItemState<List<Book>> = FetchItemState.Loading,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val libraryUseCase: ComlibUseCases,
) : StatefulViewModel<HomeScreenState>(HomeScreenState()) {

    init {
        getReadBooks()
        getAvailableBooks()
    }

    private fun getReadBooks() {
        viewModelScope.launch {
            libraryUseCase.getReadBooksUseCase().collectLatest {
                update { copy(reads = it.toList()) }
            }
        }
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            libraryUseCase.getAllBooksUseCase().collectLatest {
                val value = when (it) {
                    DataResult.Empty -> FetchItemState.Success(emptyList())
                    is DataResult.Error -> FetchItemState.Error(message = it.message)
                    is DataResult.Loading -> FetchItemState.Loading
                    is DataResult.Success -> FetchItemState.Success(it.data)
                }
                update { copy(availableState = value) }
            }
        }
    }

    private suspend fun getUserProfile(userId: String): UserProfileState {
        val profile = libraryUseCase.getUserProfileUseCase(userId)
        return if (profile == null) {
            UserProfileState.Error(message = "Could not fetch profile")
        } else {
            UserProfileState.Success(user = profile)
        }
    }

    fun onClickRetryGetReads() {
        getReadBooks()
    }

    fun onClickRetryGetAvailableBooks(){
        getAvailableBooks()
    }

//    val userProfileState: StateFlow<UserProfileState> =
//        comlibUseCases.getUserPrefsUseCase().map { prefs ->
//            prefs.userId?.let { userId ->
//                getUserProfile(userId)
//            } ?: UserProfileState.Error(message = "Could not fetch profile")
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = UserProfileState.Loading
//        )
//
//    val timePeriodState: StateFlow<TimePeriod> = flow {
//        val currHour = Instant.now().atZone(ZoneId.systemDefault()).hour
//        val time = if (currHour < 12) {
//            TimePeriod.MORNING
//        } else if (currHour < 16) {
//            TimePeriod.AFTERNOON
//        } else {
//            TimePeriod.EVENING
//        }
//        emit(time)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_0000),
//        initialValue = TimePeriod.MORNING
//    )
//
//
//    val streakState: StateFlow<StreakState>
//        get() = comlibUseCases.getStreakUseCase().distinctUntilChanged().mapLatest {
//            StreakState(it)
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = StreakState()
//        )
//
////    val networkStatus: StateFlow<NetworkStatus>
////        get() = comlibUseCases.getNetworkConnectivityUseCase.networkStatus.map { it }.stateIn(
////            scope = viewModelScope,
////            started = SharingStarted.WhileSubscribed(5_000),
////            NetworkStatus.Unavailable
////        )
//
//    private val readBooks: Flow<List<String>> = comlibUseCases.getUserPrefsUseCase().map {
//        it.readBooks.toList()
//    }
//
//    private val bookMarkedBooks: Flow<List<String>> = comlibUseCases.getUserPrefsUseCase().map {
//        it.bookmarkedBooks.toList()
//    }
//
//    val booksState = combine(
//        readBooks, bookMarkedBooks, comlibUseCases.getAllBooksUseCase()
//    ) { read, bookmarked, available ->
//        when (available) {
//            is DataResult.Error -> {
//                BooksState.Error(message = available.message)
//            }
//
//            is DataResult.Empty -> {
//                BooksState.Empty
//            }
//
//            is DataResult.Loading -> {
//                BooksState.Loading
//            }
//
//            is DataResult.Success -> {
//                BooksState.Success(available = available.data,
//                    readBooks = available.data.filter { book ->
//                        book.id in read
//                    },
//                    bookmarkedBooks = available.data.filter { book -> book.id in bookmarked })
//            }
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = BooksState.Empty
//    )
//
//
//    fun onEvent(event: HomeUiEvent) {
//        when (event) {
//            HomeUiEvent.Refresh -> {
//                viewModelScope.launch {
////                    setupData()
//                }
//            }
//
//            HomeUiEvent.NetworkRefresh -> {
//                //TODO Implement network refresh
//            }
//
//            is HomeUiEvent.SaveStreak -> {
//                saveMilestone(event.bookMilestone)
//            }
//        }
//    }
//
//    private fun saveMilestone(bookMilestone: BookMilestone) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                comlibUseCases.saveStreakUseCase(bookMilestone)
//            }
//        }
//    }
//
//    private suspend fun getUserProfile(userId: String): UserProfileState {
//        val profile = comlibUseCases.getUserProfileUseCase(userId)
//        return if (profile == null) {
//            UserProfileState.Error(message = "Could not fetch profile")
//        } else {
//            UserProfileState.Success(user = profile)
//        }
//    }
}
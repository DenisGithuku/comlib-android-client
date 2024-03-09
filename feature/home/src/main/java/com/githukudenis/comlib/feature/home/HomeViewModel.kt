package com.githukudenis.comlib.feature.home

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

data class HomeScreenState(
    val user: FetchItemState<User?> = FetchItemState.Loading,
    val reads: List<String> = emptyList(),
    val bookmarks: List<String> = emptyList(),
    val streakState: StreakState = StreakState(),
    val availableState: FetchItemState<List<BookUiModel>> = FetchItemState.Loading,
    val timePeriod: TimePeriod = TimePeriod.MORNING
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookMarkUseCase: ToggleBookMarkUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase
) : StatefulViewModel<HomeScreenState>(HomeScreenState()) {

    init {
        getTimePeriod()
        getBookmarkedBooks()
        getReadBooks()
        getUserDetails()
        getAvailableBooks()
        getStreakState()
    }

    private fun getReadBooks() {
        viewModelScope.launch {
            getReadBooksUseCase().collectLatest {
                update { copy(reads = it.toList()) }
            }
        }
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            getAllBooksUseCase().collectLatest { result ->
                val value = when (result) {
                    DataResult.Empty -> FetchItemState.Success(emptyList())
                    is DataResult.Error -> FetchItemState.Error(message = result.message)
                    is DataResult.Loading -> FetchItemState.Loading
                    is DataResult.Success -> FetchItemState.Success(result.data.map {
                        BookUiModel(
                            book = it,
                            isFavourite = it.id in state.value.bookmarks
                        )
                    })
                }
                update { copy(availableState = value) }
            }
        }
    }

    private suspend fun getUserProfile(userId: String) {
        val profile = getUserProfileUseCase(userId)
        if (profile == null) {
            update { copy(user = FetchItemState.Error(message = "Could not fetch profile")) }
        } else {
            update { copy(FetchItemState.Success(data = profile)) }
        }
    }

    fun onClickRetryGetReads() {
        getReadBooks()
    }

    fun onRefreshAvailableBooks() {
        getAvailableBooks()
    }

    fun onToggleFavourite(id: String) {
        viewModelScope.launch {
            val bookMarks = getBookmarkedBooksUseCase().first()
            val updatedBookMarkSet = if (id in bookMarks) {
                bookMarks.minus(id)
            } else {
                bookMarks.plus(id)
            }
            toggleBookMarkUseCase(
                updatedBookMarkSet
            )

            update { copy(bookmarks = updatedBookMarkSet.toList()) }
            onRefreshAvailableBooks()
        }
    }

    private fun getStreakState() {
        viewModelScope.launch {
            getStreakUseCase().collectLatest {
                update { copy(streakState = StreakState(bookMilestone = it)) }
            }
        }
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            getUserPrefsUseCase().collectLatest { prefs ->
                requireNotNull(prefs.userId).also {
                    getUserProfile(it)
                }
            }
        }
    }

    private fun getBookmarkedBooks() {
        viewModelScope.launch {
            val bookmarks = getBookmarkedBooksUseCase().first()
            update { copy(bookmarks = bookmarks.toList()) }
        }
    }

    private fun getTimePeriod() {
        val currHour = Instant.now().atZone(ZoneId.systemDefault()).hour
        val time = if (currHour < 12) {
            TimePeriod.MORNING
        } else if (currHour < 16) {
            TimePeriod.AFTERNOON
        } else {
            TimePeriod.EVENING
        }
        update { copy(timePeriod = time) }
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
package com.githukudenis.comlib.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.BookMilestone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases,
) : ViewModel() {

    val userProfileState: StateFlow<UserProfileState> =
        comlibUseCases.getUserPrefsUseCase().map { prefs ->
            prefs.userId?.let { userId ->
                getUserProfile(userId)
            } ?: UserProfileState.Error(message = "Could not fetch profile")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserProfileState.Loading
        )

    val timePeriodState: StateFlow<TimePeriod> = flow {
        val currHour = Instant.now().atZone(ZoneId.systemDefault()).hour
        val time = if (currHour < 12) {
            TimePeriod.MORNING
        } else if (currHour < 16) {
            TimePeriod.AFTERNOON
        } else {
            TimePeriod.EVENING
        }
        emit(time)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_0000),
        initialValue = TimePeriod.MORNING
    )


    val streakState: StateFlow<StreakState>
        get() = comlibUseCases.getStreakUseCase().distinctUntilChanged().mapLatest {
            StreakState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StreakState()
        )

//    val networkStatus: StateFlow<NetworkStatus>
//        get() = comlibUseCases.getNetworkConnectivityUseCase.networkStatus.map { it }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            NetworkStatus.Unavailable
//        )

    private val readBooks: Flow<List<String>> = comlibUseCases.getUserPrefsUseCase().map {
        it.readBooks.toList()
    }

    private val bookMarkedBooks: Flow<List<String>> = comlibUseCases.getUserPrefsUseCase().map {
        it.bookmarkedBooks.toList()
    }

    val booksState = combine(
        readBooks, bookMarkedBooks, comlibUseCases.getAllBooksUseCase()
    ) { read, bookmarked, available ->
        when (available) {
            is DataResult.Error -> {
                BooksState.Error(message = available.message)
            }

            is DataResult.Empty -> {
                BooksState.Empty
            }

            is DataResult.Loading -> {
                BooksState.Loading
            }

            is DataResult.Success -> {
                BooksState.Success(available = available.data,
                    readBooks = available.data.filter { book ->
                        book.id in read
                    },
                    bookmarkedBooks = available.data.filter { book -> book.id in bookmarked })
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BooksState.Empty
    )


    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.Refresh -> {
                viewModelScope.launch {
//                    setupData()
                }
            }

            HomeUiEvent.NetworkRefresh -> {
                //TODO Implement network refresh
            }

            is HomeUiEvent.SaveStreak -> {
                saveMilestone(event.bookMilestone)
            }
        }
    }

    private fun saveMilestone(bookMilestone: BookMilestone) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                comlibUseCases.saveStreakUseCase(bookMilestone)
            }
        }
    }

    private suspend fun getUserProfile(userId: String): UserProfileState {
        val profile = comlibUseCases.getUserProfileUseCase(userId)
        return if (profile == null) {
            UserProfileState.Error(message = "Could not fetch profile")
        } else {
            UserProfileState.Success(user = profile)
        }
    }
}
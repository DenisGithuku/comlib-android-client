package com.githukudenis.comlib.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.NetworkStatus
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases,
    private val bookMilestoneRepository: BookMilestoneRepository
) : ViewModel() {

    private var userProfileState: MutableStateFlow<UserProfileState> =
        MutableStateFlow(UserProfileState.Loading)

    val currentMilestone: StateFlow<BookMilestone>
        get() = bookMilestoneRepository.bookMilestone
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookMilestone()
            )


    private val networkStatus: StateFlow<NetworkStatus> =
        comlibUseCases.getNetworkConnectivityUseCase.networkStatus
            .onEach { netStatus ->
                _showNetworkDialog.update {
                    netStatus == NetworkStatus.Unavailable || netStatus == NetworkStatus.Lost
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NetworkStatus.Unknown
            )

    private var _showNetworkDialog: MutableStateFlow<Boolean> = MutableStateFlow(
        false
    )
    val showNetworkDialog: StateFlow<Boolean> = _showNetworkDialog.asStateFlow()


    private var booksState: MutableStateFlow<BooksState> = MutableStateFlow(BooksState.Loading)

    val state: StateFlow<HomeUiState>
        get() = combine(
            userProfileState, booksState, networkStatus
        ) { profile, books, networkStatus ->
            if (networkStatus == NetworkStatus.Unavailable || networkStatus == NetworkStatus.Lost) {
                HomeUiState.Error(
                    message = "Could not connect. Please check your internet connection then try again."
                )
            } else {
                HomeUiState.Success(
                    booksState = books,
                    userProfileState = profile,
                    timePeriod = comlibUseCases.getTimePeriodUseCase()
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )


    init {
        setupData()
    }

    private var bookJob: Job? = null
    private fun setupData() {
        bookJob?.cancel()
        bookJob = viewModelScope.launch {
            comlibUseCases.getUserPrefsUseCase().distinctUntilChanged().catch { error ->
                Timber.tag("prefs").d(error.message.toString())
            }.collectLatest { prefs ->
                prefs.userId?.let { userId ->
                    getUserProfile(userId)
                }

                getBooks(
                    readBooks = prefs.readBooks, bookmarkedBooks = prefs.bookmarkedBooks
                )
            }
        }
    }

    private suspend fun getBooks(
        readBooks: Set<String>, bookmarkedBooks: Set<String>
    ) {
        comlibUseCases.getAllBooksUseCase().catch { err ->
            booksState.update {
                BooksState.Error(message = err.message ?: "Could not fetch books.")
            }
        }.collectLatest { result ->
            when (result) {
                is DataResult.Error -> {
                    booksState.update {
                        BooksState.Error(message = result.message)
                    }
                }

                is DataResult.Empty -> {
                    booksState.update { BooksState.Empty }
                }

                is DataResult.Loading -> {
                    booksState.update { BooksState.Loading }
                }

                is DataResult.Success -> {
                    booksState.update {
                        BooksState.Success(available = result.data,
                            readBooks = result.data.filter { book ->
                                book.id in readBooks
                            },
                            bookmarkedBooks = result.data.filter { book -> book.id in bookmarkedBooks })
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.Refresh -> {
                setupData()
            }

            HomeUiEvent.NetworkRefresh -> {
                //TODO Implement network refresh
            }
        }
    }

    private suspend fun getUserProfile(userId: String) {
        userProfileState.update { UserProfileState.Loading }
        val profile = comlibUseCases.getUserProfileUseCase(userId)
        if (profile == null) {
            userProfileState.update { UserProfileState.Error(message = "Could not fetch profile") }
        } else {
            userProfileState.update { UserProfileState.Success(user = profile) }
        }
    }

    fun onDismissNetworkDialog() {
        _showNetworkDialog.update { false }
    }
}
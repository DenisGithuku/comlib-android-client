package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "userPrefsRepository", "Lcom/githukudenis/comlib/core/data/repository/UserPrefsRepository;", "userRepository", "Lcom/githukudenis/comlib/core/data/repository/UserRepository;", "(Lcom/githukudenis/comlib/core/data/repository/UserPrefsRepository;Lcom/githukudenis/comlib/core/data/repository/UserRepository;)V", "state", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiState;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "settings_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.data.repository.UserPrefsRepository userPrefsRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.githukudenis.comlib.feature.settings.SettingsUiState> state = null;
    
    @javax.inject.Inject
    public SettingsViewModel(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.data.repository.UserPrefsRepository userPrefsRepository, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.data.repository.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.githukudenis.comlib.feature.settings.SettingsUiState> getState() {
        return null;
    }
}
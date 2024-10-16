package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "userPrefsRepository", "Lcom/githukudenis/comlib/core/data/repository/UserPrefsRepository;", "userRepository", "Lcom/githukudenis/comlib/core/data/repository/UserRepository;", "(Lcom/githukudenis/comlib/core/data/repository/UserPrefsRepository;Lcom/githukudenis/comlib/core/data/repository/UserRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "changeTheme", "", "themeConfig", "Lcom/githukudenis/comlib/core/model/ThemeConfig;", "getUserDetails", "onChangePhoto", "value", "Landroid/net/Uri;", "onEvent", "event", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "resetUpdateError", "toggleAppearance", "isToggled", "", "toggleClearCache", "toggleNotifications", "toggleThemeDialog", "settings_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.data.repository.UserPrefsRepository userPrefsRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.githukudenis.comlib.feature.settings.SettingsUiState> _state = null;
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
    
    private final void getUserDetails() {
    }
    
    public final void onEvent(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.SettingsUiEvent event) {
    }
    
    private final void resetUpdateError() {
    }
    
    private final void changeTheme(com.githukudenis.comlib.core.model.ThemeConfig themeConfig) {
    }
    
    private final void toggleNotifications(boolean isToggled) {
    }
    
    private final void toggleAppearance(boolean isToggled) {
    }
    
    private final void toggleClearCache(boolean isToggled) {
    }
    
    private final void toggleThemeDialog(boolean isToggled) {
    }
    
    private final void onChangePhoto(android.net.Uri value) {
    }
}
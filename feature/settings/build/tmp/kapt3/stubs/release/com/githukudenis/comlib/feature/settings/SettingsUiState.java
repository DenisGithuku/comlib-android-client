package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u000bH\u00c6\u0003JA\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiState;", "", "profileItemState", "Lcom/githukudenis/comlib/feature/settings/ProfileItemState;", "availableThemes", "", "Lcom/githukudenis/comlib/core/model/ThemeConfig;", "selectedTheme", "isNotificationsToggled", "", "uiComponentsState", "Lcom/githukudenis/comlib/feature/settings/UiComponentsState;", "(Lcom/githukudenis/comlib/feature/settings/ProfileItemState;Ljava/util/List;Lcom/githukudenis/comlib/core/model/ThemeConfig;ZLcom/githukudenis/comlib/feature/settings/UiComponentsState;)V", "getAvailableThemes", "()Ljava/util/List;", "()Z", "getProfileItemState", "()Lcom/githukudenis/comlib/feature/settings/ProfileItemState;", "getSelectedTheme", "()Lcom/githukudenis/comlib/core/model/ThemeConfig;", "getUiComponentsState", "()Lcom/githukudenis/comlib/feature/settings/UiComponentsState;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "settings_release"})
public final class SettingsUiState {
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.feature.settings.ProfileItemState profileItemState = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> availableThemes = null;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.model.ThemeConfig selectedTheme = null;
    private final boolean isNotificationsToggled = false;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.feature.settings.UiComponentsState uiComponentsState = null;
    
    public SettingsUiState(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.ProfileItemState profileItemState, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.githukudenis.comlib.core.model.ThemeConfig> availableThemes, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.ThemeConfig selectedTheme, boolean isNotificationsToggled, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.UiComponentsState uiComponentsState) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.ProfileItemState getProfileItemState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> getAvailableThemes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.ThemeConfig getSelectedTheme() {
        return null;
    }
    
    public final boolean isNotificationsToggled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.UiComponentsState getUiComponentsState() {
        return null;
    }
    
    public SettingsUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.ProfileItemState component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.ThemeConfig component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.UiComponentsState component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.SettingsUiState copy(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.ProfileItemState profileItemState, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.githukudenis.comlib.core.model.ThemeConfig> availableThemes, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.ThemeConfig selectedTheme, boolean isNotificationsToggled, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.UiComponentsState uiComponentsState) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}
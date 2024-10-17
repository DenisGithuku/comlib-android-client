package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bq\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0004\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\tH\u00c6\u0003J\t\u0010!\u001a\u00020\tH\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\t\u0010#\u001a\u00020\tH\u00c6\u0003J\t\u0010$\u001a\u00020\tH\u00c6\u0003Ju\u0010%\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00c6\u0001J\u0013\u0010&\u001a\u00020\t2\b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010(\u001a\u00020)H\u00d6\u0001J\t\u0010*\u001a\u00020\u0010H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0014R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0014R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0014R\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0014R\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006+"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiState;", "", "availableThemes", "", "Lcom/githukudenis/comlib/core/model/ThemeConfig;", "userProfileData", "Lcom/githukudenis/comlib/core/model/UserProfileData;", "selectedTheme", "isNotificationsToggled", "", "isCacheDialogVisible", "isAppearanceSheetVisible", "isThemeDialogVisible", "isUpdating", "isUpdateComplete", "updateError", "", "(Ljava/util/List;Lcom/githukudenis/comlib/core/model/UserProfileData;Lcom/githukudenis/comlib/core/model/ThemeConfig;ZZZZZZLjava/lang/String;)V", "getAvailableThemes", "()Ljava/util/List;", "()Z", "getSelectedTheme", "()Lcom/githukudenis/comlib/core/model/ThemeConfig;", "getUpdateError", "()Ljava/lang/String;", "getUserProfileData", "()Lcom/githukudenis/comlib/core/model/UserProfileData;", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "settings_debug"})
public final class SettingsUiState {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> availableThemes = null;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.model.UserProfileData userProfileData = null;
    @org.jetbrains.annotations.NotNull
    private final com.githukudenis.comlib.core.model.ThemeConfig selectedTheme = null;
    private final boolean isNotificationsToggled = false;
    private final boolean isCacheDialogVisible = false;
    private final boolean isAppearanceSheetVisible = false;
    private final boolean isThemeDialogVisible = false;
    private final boolean isUpdating = false;
    private final boolean isUpdateComplete = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String updateError = null;
    
    public SettingsUiState(@org.jetbrains.annotations.NotNull
    java.util.List<? extends com.githukudenis.comlib.core.model.ThemeConfig> availableThemes, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.UserProfileData userProfileData, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.ThemeConfig selectedTheme, boolean isNotificationsToggled, boolean isCacheDialogVisible, boolean isAppearanceSheetVisible, boolean isThemeDialogVisible, boolean isUpdating, boolean isUpdateComplete, @org.jetbrains.annotations.Nullable
    java.lang.String updateError) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> getAvailableThemes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.UserProfileData getUserProfileData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.ThemeConfig getSelectedTheme() {
        return null;
    }
    
    public final boolean isNotificationsToggled() {
        return false;
    }
    
    public final boolean isCacheDialogVisible() {
        return false;
    }
    
    public final boolean isAppearanceSheetVisible() {
        return false;
    }
    
    public final boolean isThemeDialogVisible() {
        return false;
    }
    
    public final boolean isUpdating() {
        return false;
    }
    
    public final boolean isUpdateComplete() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getUpdateError() {
        return null;
    }
    
    public SettingsUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.githukudenis.comlib.core.model.ThemeConfig> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.UserProfileData component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.core.model.ThemeConfig component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.githukudenis.comlib.feature.settings.SettingsUiState copy(@org.jetbrains.annotations.NotNull
    java.util.List<? extends com.githukudenis.comlib.core.model.ThemeConfig> availableThemes, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.UserProfileData userProfileData, @org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.core.model.ThemeConfig selectedTheme, boolean isNotificationsToggled, boolean isCacheDialogVisible, boolean isAppearanceSheetVisible, boolean isThemeDialogVisible, boolean isUpdating, boolean isUpdateComplete, @org.jetbrains.annotations.Nullable
    java.lang.String updateError) {
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
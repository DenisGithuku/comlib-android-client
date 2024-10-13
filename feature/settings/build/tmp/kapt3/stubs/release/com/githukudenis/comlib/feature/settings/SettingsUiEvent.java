package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0005\u0002\u0003\u0004\u0005\u0006\u0082\u0001\u0005\u0007\b\t\n\u000b\u00a8\u0006\f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "", "ChangeTheme", "ToggleAppearance", "ToggleClearCache", "ToggleNotifications", "ToggleThemeDialog", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ChangeTheme;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleAppearance;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleClearCache;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleNotifications;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleThemeDialog;", "settings_release"})
public abstract interface SettingsUiEvent {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ChangeTheme;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "themeConfig", "Lcom/githukudenis/comlib/core/model/ThemeConfig;", "(Lcom/githukudenis/comlib/core/model/ThemeConfig;)V", "getThemeConfig", "()Lcom/githukudenis/comlib/core/model/ThemeConfig;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "settings_release"})
    public static final class ChangeTheme implements com.githukudenis.comlib.feature.settings.SettingsUiEvent {
        @org.jetbrains.annotations.NotNull
        private final com.githukudenis.comlib.core.model.ThemeConfig themeConfig = null;
        
        public ChangeTheme(@org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.core.model.ThemeConfig themeConfig) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.core.model.ThemeConfig getThemeConfig() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.core.model.ThemeConfig component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.settings.SettingsUiEvent.ChangeTheme copy(@org.jetbrains.annotations.NotNull
        com.githukudenis.comlib.core.model.ThemeConfig themeConfig) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleAppearance;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "isToggled", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "settings_release"})
    public static final class ToggleAppearance implements com.githukudenis.comlib.feature.settings.SettingsUiEvent {
        private final boolean isToggled = false;
        
        public ToggleAppearance(boolean isToggled) {
            super();
        }
        
        public final boolean isToggled() {
            return false;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.settings.SettingsUiEvent.ToggleAppearance copy(boolean isToggled) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleClearCache;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "isToggled", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "settings_release"})
    public static final class ToggleClearCache implements com.githukudenis.comlib.feature.settings.SettingsUiEvent {
        private final boolean isToggled = false;
        
        public ToggleClearCache(boolean isToggled) {
            super();
        }
        
        public final boolean isToggled() {
            return false;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.settings.SettingsUiEvent.ToggleClearCache copy(boolean isToggled) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleNotifications;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "isToggled", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "settings_release"})
    public static final class ToggleNotifications implements com.githukudenis.comlib.feature.settings.SettingsUiEvent {
        private final boolean isToggled = false;
        
        public ToggleNotifications(boolean isToggled) {
            super();
        }
        
        public final boolean isToggled() {
            return false;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.settings.SettingsUiEvent.ToggleNotifications copy(boolean isToggled) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent$ToggleThemeDialog;", "Lcom/githukudenis/comlib/feature/settings/SettingsUiEvent;", "isToggled", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "settings_release"})
    public static final class ToggleThemeDialog implements com.githukudenis.comlib.feature.settings.SettingsUiEvent {
        private final boolean isToggled = false;
        
        public ToggleThemeDialog(boolean isToggled) {
            super();
        }
        
        public final boolean isToggled() {
            return false;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.githukudenis.comlib.feature.settings.SettingsUiEvent.ToggleThemeDialog copy(boolean isToggled) {
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
}
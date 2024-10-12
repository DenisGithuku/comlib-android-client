package com.githukudenis.comlib.feature.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a7\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\b\u0010\t\u001a,\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0012\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a,\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a.\u0010\u0014\u001a\u00020\u00012\b\b\u0002\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a:\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"ProfileImage", "", "imageUrl", "", "size", "Landroidx/compose/ui/unit/Dp;", "onChangeImage", "Lkotlin/Function0;", "ProfileImage-lG28NQ4", "(Ljava/lang/String;FLkotlin/jvm/functions/Function0;)V", "ProfileLoaded", "profile", "Lcom/githukudenis/comlib/feature/settings/Profile;", "onEditProfile", "ProfileLoader", "modifier", "Landroidx/compose/ui/Modifier;", "ProfileSection", "profileItemState", "Lcom/githukudenis/comlib/feature/settings/ProfileItemState;", "SettingsRoute", "viewModel", "Lcom/githukudenis/comlib/feature/settings/SettingsViewModel;", "onNavigateUp", "onOpenEditProfile", "SettingsScreen", "state", "Lcom/githukudenis/comlib/feature/settings/SettingsUiState;", "settings_debug"})
public final class SettingsRouteKt {
    
    @androidx.compose.runtime.Composable
    public static final void SettingsRoute(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.SettingsViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateUp, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenEditProfile) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.SettingsUiState state, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateUp, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onChangeImage, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditProfile) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileSection(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.ProfileItemState profileItemState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onChangeImage, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditProfile) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileLoader(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileLoaded(@org.jetbrains.annotations.NotNull
    com.githukudenis.comlib.feature.settings.Profile profile, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onChangeImage, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditProfile) {
    }
}
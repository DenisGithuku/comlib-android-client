plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.library.compose)
    alias(libs.plugins.comlib.android.hilt)
}

android {
    namespace = "com.githukudenis.comlib.core.testing"
    hilt { enableAggregatingTask = true }
}

dependencies {
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.turbine)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockito.core)

    debugApi(libs.androidx.compose.ui.test.manifest)

    //    implementation(project(":core:data"))
    //    implementation(project(":core:model"))
    //    implementation(project(":core:common"))
    implementation(libs.kotlinx.datetime)
}

plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.library.compose)
    alias(libs.plugins.comlib.android.feature)
    alias(libs.plugins.comlib.android.hilt)
}

android {
    namespace = "com.githukudenis.comlib.feature.add_book"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
}

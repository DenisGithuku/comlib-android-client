plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.library.compose)
    alias(libs.plugins.comlib.android.library.firebase)
    alias(libs.plugins.comlib.android.feature)
}

android {
    namespace = "com.githukudenis.comlib.feature.auth"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))

    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.gms.play.services.auth)

    testImplementation(libs.junit4)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
}

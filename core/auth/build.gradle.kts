plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.library.compose)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.library.firebase)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.auth"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:testing"))

    implementation(libs.firebase.bom)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.timber)
    implementation(libs.bundles.ktor)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit4)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.espresso.core)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
}

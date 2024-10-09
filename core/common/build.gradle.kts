@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.common"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.ktor)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
}

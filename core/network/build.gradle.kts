plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.library.firebase)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.network"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))

    implementation(libs.bundles.ktor)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.core.ktx)
    implementation(libs.timber)
}

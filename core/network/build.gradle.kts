plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.library.firebase)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.network"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))

    implementation(libs.ktor.client)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.core.ktx)
}

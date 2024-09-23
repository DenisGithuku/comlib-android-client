plugins {
    alias(libs.plugins.comlib.android.library)
    id("kotlinx-serialization")
}

android { namespace = "com.githukudenis.comlib.core.model" }

dependencies {
    implementation(libs.ktor.serialization)
    implementation(libs.timber)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.feature)
}

android { namespace = "com.githukudenis.comlib.core.domain" }

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.timber)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

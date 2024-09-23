plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.room)
}

android {
    namespace = "com.githukudenis.comlib.core.database"
    hilt { enableAggregatingTask = true }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
}

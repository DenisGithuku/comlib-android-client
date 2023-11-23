plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
}

android {
    namespace = "com.githukudenis.comlib.core.data_test"
}

dependencies {

    api(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
}
plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.data"


    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.timber)

    implementation(libs.ktor.serialization)
    implementation(libs.ktor.client)
    implementation(libs.ktor.logging)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)

}
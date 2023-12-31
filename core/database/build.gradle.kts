plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.room)
}

android {
    namespace = "com.githukudenis.comlib.core.database"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
}
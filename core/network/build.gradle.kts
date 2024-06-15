plugins {
    alias(libs.plugins.comlib.android.library)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.library.firebase)
    id("kotlinx-serialization")
}

android {
    namespace = "com.githukudenis.comlib.core.network"

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
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))

    implementation(libs.ktor.client)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.core.ktx)
}
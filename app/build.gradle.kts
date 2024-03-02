plugins {
    alias(libs.plugins.comlib.android.application)
    alias(libs.plugins.comlib.android.application.compose)
    alias(libs.plugins.comlib.android.hilt)
    alias(libs.plugins.comlib.android.application.firebase)
}

android {
    namespace = "com.githukudenis.comlib"

    defaultConfig {
        applicationId = "com.githukudenis.comlib"
        versionCode = 1
        versionName = "1.0-alpha01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystore/comlibdebug.keystore")
            keyAlias = "comlib"
            keyPassword = "comlibdroid"
            storePassword = "04uth50ft!5f4"
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:book_detail"))
    implementation(project(":feature:books"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:add_book"))
    implementation(project(":feature:my_books"))
    implementation(project(":feature:genre_setup"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.timber)
    implementation(libs.work.runtime)

    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
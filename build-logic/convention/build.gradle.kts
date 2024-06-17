import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.githukudenis.comlib.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "comlib.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "comlib.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
//        register("androidLint") {
//            id = "comlib.android.lint"
//            implementationClass = "AndroidLintConventionPlugin"
//        }
        register("androidApplicationCompose") {
            id = "comlib.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "comlib.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplicationFirebase") {
            id = "comlib.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidLibraryFirebase") {
            id = "comlib.android.library.firebase"
            implementationClass = "AndroidLibraryFirebaseConventionPlugin"
        }
        register("androidHilt") {
            id = "comlib.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidTest") {
            id = "comlib.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidRoom") {
            id = "comlib.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFeature") {
            id = "comlib.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}
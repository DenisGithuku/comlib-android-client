import com.android.build.api.dsl.LibraryExtension
import com.githukudenis.comlib.AndroidSdk
import com.githukudenis.comlib.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with (pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
//                apply("comlib.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidSdk.targetSdk
            }

            dependencies {
                add("testImplementation", kotlin("test"))
//                add("testImplementation", project(":core-testing"))
                add("androidTestImplementation", kotlin("test"))
//                add("androidTestImplementation", project(":core-testing"))
            }
        }
    }
}
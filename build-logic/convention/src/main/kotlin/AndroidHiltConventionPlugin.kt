import com.githukudenis.comlib.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.kapt")
            }
            dependencies{
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(libs.findLibrary("hilt.work").get())
                "implementation"(libs.findLibrary("hilt.common").get())
                "kapt"(libs.findLibrary("android.hilt.androidx.compiler").get())
                "kapt"(libs.findLibrary("androidx.hilt.compiler").get())
                "kaptAndroidTest"(libs.findLibrary("androidx.hilt.compiler").get())
                "kaptTest"(libs.findLibrary("androidx.hilt.compiler").get())
                "androidTestImplementation"(libs.findLibrary("androidx.hilt.testing").get())
                "testImplementation"(libs.findLibrary("androidx.hilt.compiler").get())
            }
        }
    }
}

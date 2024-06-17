//class AndroidLintConventionPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        with(target) {
//            when {
//                pluginManager.hasPlugin("com.android.application") -> configure<ApplicationExtension> {
//                    lint(
//                        Lint::configure
//                    )
//                }
//
//                pluginManager.hasPlugin("com.android.library") -> configure<LibraryExtension> {
//                    lint(
//                        Lint::configure
//                    )
//                }
//
//                else -> {
//                    pluginManager.apply("com.android.lint")
//                    configure<Lint>(Lint::configure)
//                }
//            }
//        }
//    }
//}
//
//private fun Lint.configure() {
//    xmlReport = true
//    checkDependencies = true
//}
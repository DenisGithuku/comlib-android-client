pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComLib"
include(":app")
include(":core:data")
include(":core:auth")
include(":core:model")
include(":core:network")
include(":feature:auth")
include(":core:testing")
include(":core:common")
include(":core:designsystem")
include(":feature:home")
include(":core:domain")
include(":core:datastore")
include(":feature:book_detail")

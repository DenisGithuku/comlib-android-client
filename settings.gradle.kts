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
include(":core:datastore")
include(":feature:book_detail")
include(":feature:books")
include(":feature:settings")
include(":feature:profile")
include(":core:data-test")
include(":feature:add_book")
include(":feature:my_books")
include(":core:database")
include(":feature:genre_setup")
include(":feature:streak")

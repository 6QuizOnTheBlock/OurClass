enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
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
    includeBuild("build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ulban"
include(":app")
include(":domain")
include(":data")
include(":core:ui")
include(":core:model")
include(":core:designsystem")
include(":feature:navigator")
include(":feature:home")
include(":feature:signin")
include(":feature:classroom")
include(":feature:teacher:home")
include(":feature:teacher:board")

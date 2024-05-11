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
        maven(url = uri("https://jitpack.io"))
        maven (url ="https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "ulban"
include(":app")
include(":domain")
include(":data")
include(":core:ui")
include(":core:model")
include(":core:designsystem")
include(":core:nfc")
include(":feature:navigator")
include(":feature:home")
include(":feature:signin")
include(":feature:classroom")
include(":feature:teacher:home")
include(":feature:teacher:board")
include(":feature:teacher:manageclass")
include(":feature:teacher:managestudent")
include(":feature:student:home")
include(":feature:student:board")
include(":feature:student:challenge")
include(":feature:student:relay")
include(":feature:teacher:challenge")
include(":feature:teacher:main")
include(":core:bluetooth")
include(":feature:student:main")

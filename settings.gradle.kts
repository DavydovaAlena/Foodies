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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Foodies"
include(":app")
include(":feature:catalog-feature")
include(":feature:product-card-feature")
include(":feature:search-screen-feature")
include(":foodies-database")
include(":foodiesapi")
include(":foodies-data")
include(":feature:component")
include(":feature:basket-feature")

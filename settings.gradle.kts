pluginManagement {
    repositories {
        google()
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

rootProject.name = "CineTrack"
include(":app")

// NOTE (Phase 1): modul-modul baru akan ditambahkan di sini saat kita modularisasi,
// contoh: include(":core:network", ":core:database", ":feature:movies", ":feature:search")

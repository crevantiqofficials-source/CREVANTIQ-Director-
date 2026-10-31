// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext {
        kotlin_version = "2.0.21"
        agp_version = "8.7.3"
        compose_bom_version = "2024.10.01"
        gradle_version = "8.7"
    }
}

plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}

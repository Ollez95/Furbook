buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()  // Maven Central repository

    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath(libs.hilt.android.gradle.plugin)
    }

}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.secret.plugin) apply false
}
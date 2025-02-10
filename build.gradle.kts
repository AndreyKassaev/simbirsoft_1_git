// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}
ktlint {
    version.set("0.45.2")
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
}

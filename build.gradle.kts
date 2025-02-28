// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.google.devtools.ksp") version "2.1.10-1.0.30" apply false
    id("com.google.dagger.hilt.android") version "2.55" apply false
    id("androidx.room") version "2.6.1" apply false
    alias(libs.plugins.android.library) apply false
}
ktlint {
    version.set("0.45.2")
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.10"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("androidx.room")
}

android {
    namespace = "com.kassaev.simbirsoft_1_git"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kassaev.simbirsoft_1_git"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/main/assets")
            }
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation(libs.retrofit)
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation(libs.retrofit2.converter.gson)
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/logging-interceptor
    implementation(libs.logging.interceptor)

    //Compose Navigation
    // https://mvnrepository.com/artifact/androidx.navigation/navigation-compose
    implementation(libs.androidx.navigation.compose)

    //Serialization
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm
    implementation(libs.kotlinx.serialization.json)

    //Coil
    //https://coil-kt.github.io/coil/compose/
    implementation(libs.coil.compose)
    //By default, Coil 3.x does not include support for loading images from the network.
    implementation(libs.coil.network.okhttp)
    // https://coil-kt.github.io/coil/gifs/
    implementation(libs.coil.gif)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Dagger - DI
    // https://mvnrepository.com/artifact/com.google.dagger/dagger-android
    implementation(libs.dagger.android)
    // https://mvnrepository.com/artifact/com.google.dagger/dagger-compiler
    kapt(libs.dagger.compiler)


    //Hilt - DI, to deal with stavedStateHandle
    // https://mvnrepository.com/artifact/androidx.hilt/hilt-navigation-compose
    implementation(libs.androidx.hilt.navigation.compose)
    // https://mvnrepository.com/artifact/com.google.dagger/hilt-android
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Module
    implementation(project(":feature_history"))
    implementation(project(":core"))
}
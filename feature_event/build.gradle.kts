plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.kassaev.simbirsoft_1_git.feature.event"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Module
    implementation(project(":core"))

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3)

    //Compose Navigation
    // https://mvnrepository.com/artifact/androidx.navigation/navigation-compose
    implementation(libs.androidx.navigation.compose)

    //Hilt - DI, to deal with stavedStateHandle
    // https://mvnrepository.com/artifact/androidx.hilt/hilt-navigation-compose
    implementation(libs.androidx.hilt.navigation.compose)
    // https://mvnrepository.com/artifact/com.google.dagger/hilt-android
    implementation(libs.hilt.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    kapt(libs.hilt.android.compiler)

    //Coil
    //https://coil-kt.github.io/coil/compose/
    implementation(libs.coil.compose)
    //By default, Coil 3.x does not include support for loading images from the network.
    implementation(libs.coil.network.okhttp)
    // https://coil-kt.github.io/coil/gifs/
    implementation(libs.coil.gif)

    //Worker
    // https://mvnrepository.com/artifact/androidx.hilt/hilt-work
    implementation("androidx.hilt:hilt-work:1.2.0")
    // https://mvnrepository.com/artifact/androidx.work/work-runtime-ktx
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    //Preferences DataStore
    implementation(libs.androidx.datastore.preferences)

    //Serialization
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm
    implementation(libs.kotlinx.serialization.json)
}
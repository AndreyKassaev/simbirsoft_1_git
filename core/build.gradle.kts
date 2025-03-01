plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.1.10"
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("androidx.room")
}

android {
    namespace = "com.kassaev.simbirsoft_1_git.core"
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
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3)

    //Compose Navigation
    // https://mvnrepository.com/artifact/androidx.navigation/navigation-compose
    implementation(libs.androidx.navigation.compose)

    //Serialization
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm
    implementation(libs.kotlinx.serialization.json)

    //Hilt - DI, to deal with stavedStateHandle
    // https://mvnrepository.com/artifact/androidx.hilt/hilt-navigation-compose
    implementation(libs.androidx.hilt.navigation.compose)
    // https://mvnrepository.com/artifact/com.google.dagger/hilt-android
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Retrofit
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation(libs.retrofit)
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation(libs.retrofit2.converter.gson)
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/logging-interceptor
    implementation(libs.logging.interceptor)


    //Coil
    //https://coil-kt.github.io/coil/compose/
    implementation(libs.coil.compose)
    //By default, Coil 3.x does not include support for loading images from the network.
    implementation(libs.coil.network.okhttp)
    // https://coil-kt.github.io/coil/gifs/
    implementation(libs.coil.gif)

    //Preferences DataStore
    implementation(libs.androidx.datastore.preferences)
}
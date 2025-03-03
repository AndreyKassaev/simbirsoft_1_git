import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.kassaev.simbirsoft_1_git.feature.help"
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
    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
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
    kapt(libs.hilt.android.compiler)

    //Coil
    //https://coil-kt.github.io/coil/compose/
    implementation(libs.coil.compose)
    //By default, Coil 3.x does not include support for loading images from the network.
    implementation(libs.coil.network.okhttp)
    // https://coil-kt.github.io/coil/gifs/
    implementation(libs.coil.gif)

    //Test
    // https://mvnrepository.com/artifact/androidx.compose.ui/ui-test-junit4
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.8.0-beta02")
    // https://mvnrepository.com/artifact/androidx.compose.ui/ui-test-manifest
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.8.0-beta02")
    //https://mvnrepository.com/artifact/io.mockk/mockk
    androidTestImplementation("io.mockk:mockk-android:1.13.17")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.12.0")
    androidTestImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/com.google.dagger/hilt-android-testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.55")

}
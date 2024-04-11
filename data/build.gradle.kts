plugins {
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.androidLibrary)
    kotlin("kapt")
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization") version embeddedKotlinVersion // plugin for serialization compiler
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //API key for build config
        buildConfigField("String", "API_KEY", "\"9e724c651b138a847619c1dc6d269a55\"")
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    testImplementation(libs.junit)

    // Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)

    //  serialisation
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //mockk
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.serialization.json) // testing serialization
}
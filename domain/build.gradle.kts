plugins {
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.androidLibrary)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    testImplementation(libs.junit)

    //  serialisation
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Mockk & coroutines testing
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
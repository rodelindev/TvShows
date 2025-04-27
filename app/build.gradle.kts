import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    //alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.android)
    //Compose compiler kotlin 2.0.0
    alias(libs.plugins.kotlinx.compose.compiler)
    //Serialization
    alias(libs.plugins.kotlinx.serialization)
    // Kotlin ksp
    alias(libs.plugins.ksp)
    //dagger hilt
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.rodelindev.tvshows"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rodelindev.tvshows"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties().apply {
            load(keystoreFile.inputStream())
        }
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)

    // Material Design
    implementation (libs.androidx.material)
    implementation (libs.androidx.material.icons.extended)

    // Constraint layout
    implementation (libs.androidx.constraintlayout.compose)

    // Navigation Component
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.placeholder.material)
    implementation(libs.accompanist.systemuicontroller)

    // Kotlin Serializable
    implementation(libs.kotlinx.serialization.json)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)
    kspAndroidTest(libs.hilt.android.compiler)

    // Data Persistence - Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //noinspection KaptUsageInsteadOfKsp
    ksp(libs.androidx.room.compiler)

    // Network - Retrofit, OkHttp, Moshi, Gson
    implementation(libs.retrofit)
    //implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    //implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    // Paging
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Animations - Lottie
    implementation(libs.lottie.compose)

    // Image Loading - Coil
    implementation(libs.coil.compose)

    //Testing
    // Truth
    testImplementation(libs.truth)
    // Mockk
    testImplementation(libs.mockk)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
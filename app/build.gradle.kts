import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.project.coinsight"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.coinsight"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        }
        buildConfigField(
            "String",
            "API_KEY",
            "\"${properties.getProperty("API_KEY") ?: ""}\""
        )
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
        buildConfig = true
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
    //navigation
    implementation(libs.androidx.navigation.compose)
    //hilt
    implementation(libs.dagger.hilt.android)
//    implementation(libs.androidx.navigation.compose.jvmstubs)
    implementation(libs.androidx.compose.material3)
    ksp(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    //material
    implementation (libs.material3)
    implementation (libs.androidx.material3.window.size.class1)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    // Moshi (or Gson if you prefer)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    //gson
    implementation (libs.gson)
    //square's gson
    implementation (libs.converter.gson)
    //coil
    implementation(libs.coil.compose)
    implementation (libs.logging.interceptor)
    testImplementation(libs.junit)
    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    //paging
    implementation(libs.androidx.paging.runtime)
    //charts
    implementation (libs.mpandroidchart)
    //swipe to refresh
    implementation(libs.accompanist.swiperefresh)
    //tests for flows
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
//    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation (libs.mockk)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
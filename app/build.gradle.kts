plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.adavydova.foodies"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.adavydova.foodies"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.gson)
    implementation(libs.navigation.compose)
    implementation(libs.converter.gson)

    implementation (libs.androidx.core.splashscreen)
    implementation (libs.lottie.compose)
    implementation (libs.androidx.room.ktx)
    annotationProcessor (libs.androidx.room.runtime)
    ksp( libs.androidx.room.compiler)

    implementation(project(":foodiesapi"))
    implementation(project(":foodies-database"))
    implementation(project(":foodies-data"))
    implementation(project(":feature:catalog-feature"))
    implementation(project(":feature:product-card-feature"))
    implementation(project(":feature:basket-feature"))
    implementation(project(":feature:search-screen-feature"))

}
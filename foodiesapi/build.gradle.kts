plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)

}



java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.androidx.annotation)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.kotlinx.serialization.json)

}
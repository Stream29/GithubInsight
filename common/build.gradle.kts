plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}


dependencies {
    api(libs.kotlinx.serialization.core)
    implementation(libs.ktor.core)
    implementation(libs.ktor.client.java)
}
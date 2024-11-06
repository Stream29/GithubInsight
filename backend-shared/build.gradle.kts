import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.mongodb.driver.kotlin.coroutine)
                implementation(libs.langchain4kt.core)
                implementation(libs.langchain4kt.utils)
                implementation(libs.langchain4kt.api.google.gemini)
                implementation(libs.langchain4kt.api.baidu.qianfan)
                implementation(libs.ktor.client.java)
                api(libs.ktor.core)
                api(libs.kotlinx.coroutines.core)
                api(libs.coroutines.run.blocking.all)
                api(libs.kotlinx.serialization.core)
                implementation(libs.bson.kotlinx)
                implementation(libs.kaml)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
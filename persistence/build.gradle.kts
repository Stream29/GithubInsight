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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.coroutines.run.blocking.all)
            }
        }
        val jvmTest by getting {
            dependencies {
            }
        }
    }
}
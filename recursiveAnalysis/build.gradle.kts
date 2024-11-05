plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()
    js().browser()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":githubSpider"))
                implementation(libs.langchain4kt.core)
                implementation(libs.langchain4kt.utils)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.coroutines.run.blocking.all)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.langchain4kt.core)
                implementation(libs.langchain4kt.api.baidu.qianfan)
                implementation(libs.langchain4kt.api.google.gemini)
                implementation(libs.ktor.core)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlin.test)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
            }
        }
    }
}
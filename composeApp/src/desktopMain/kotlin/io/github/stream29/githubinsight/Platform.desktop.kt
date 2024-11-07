package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.common.BackendApiProvider
import io.github.stream29.githubinsight.common.RemoteBackendApiProvider
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val httpClient = HttpClient(CIO) {
    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.SIMPLE
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    engine {
        requestTimeout = 200 * 1000
    }
    install(HttpRequestRetry) {
        retryOnException(maxRetries = 10)
        constantDelay()
    }
}

actual val backendApiProvider: BackendApiProvider =
    RemoteBackendApiProvider(httpClient, "http://127.0.0.1")
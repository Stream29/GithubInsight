package io.github.stream29.githubinsight

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

actual class GithubApiProvider actual constructor(
    val authToken: String
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    actual suspend fun <T> fetch(url: String, serializer: DeserializationStrategy<T>): T {
        val responsBody = httpClient.get(url) {
            headers {
                append("Authorization", "Bearer $authToken")
            }
            contentType(ContentType.Application.Json)
        }.bodyAsText()
        val body = json.decodeFromString(serializer, responsBody)
        return body
    }

    actual suspend fun fetchUser(username: String): User {
        return fetch("$baseUserUrl/$username", User.serializer())
    }

}
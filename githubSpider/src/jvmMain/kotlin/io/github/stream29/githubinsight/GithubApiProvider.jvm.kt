package io.github.stream29.githubinsight

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

actual class GithubApiProvider actual constructor(
    val authToken: String
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    actual suspend fun fetchUser(username: String): User {
        val responsBody = httpClient.get("https://api.github.com/users/$username") {
            headers {
                append("Authorization", "Bearer $authToken")
            }
            contentType(ContentType.Application.Json)
        }.bodyAsText()
        val body = json.decodeFromString<User>(responsBody)
        return body
    }
}
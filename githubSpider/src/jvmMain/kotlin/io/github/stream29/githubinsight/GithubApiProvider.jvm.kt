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

    actual suspend fun fetchAll(username: String) {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchUser(username: String): UserResponse {
        return fetch("$baseUserUrl/$username", UserResponse.serializer())
    }

    actual suspend fun fetchRepositories(reposUrl: String): List<RepositoryResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchReleases(releaseUrl: String): List<ReleaseResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchCommits(commitsUrl: String): List<CommitResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchIssues(issuesUrl: String): List<IssueResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
        TODO("Not yet implemented")
    }

}
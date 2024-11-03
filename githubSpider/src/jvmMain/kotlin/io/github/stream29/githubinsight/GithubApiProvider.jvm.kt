package io.github.stream29.githubinsight

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

actual class GithubApiProvider actual constructor(
    val authToken: String
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private inline fun <reified T> decodeFromString(responseBody: String): T {
        return json.decodeFromString<T>(responseBody)
    }

    actual suspend fun fetch(url: String): String {
        val responseBody = httpClient.get(url) {
            headers {
                append("Authorization", "Bearer $authToken")
            }
            contentType(ContentType.Application.Json)
        }.bodyAsText()
        return responseBody
    }

    actual suspend fun fetchAll(username: String) {
    }

    actual suspend fun fetchUser(username: String): UserResponse {
        val userJson = fetch("$baseUserUrl/$username")
        return decodeFromString<UserResponse>(userJson)
    }

    actual suspend fun fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
        val orgsJson = fetch(orgsUrl)
        return decodeFromString<List<OrganizationResponse>>(orgsJson)
    }

    actual suspend fun fetchOrganization(orgUrl: String): OrganizationResponse {
        val orgJson = fetch(orgUrl)
        return decodeFromString<OrganizationResponse>(orgJson)
    }

    actual suspend fun fetchRepositories(reposUrl: String): List<RepositoryResponse> {
        val reposJson = fetch(reposUrl)
        return decodeFromString<List<RepositoryResponse>>(reposJson)
    }

    actual suspend fun fetchReleases(releaseUrl: String): List<ReleaseResponse> {
        val releasesJson = fetch(releaseUrl.replace("{/id}", ""))
        return decodeFromString<List<ReleaseResponse>>(releasesJson)
    }

    actual suspend fun fetchCommits(commitsUrl: String): List<CommitResponse> {
        val commitsJson = fetch(commitsUrl.replace("{/sha}", ""))
        return decodeFromString<List<CommitResponse>>(commitsJson)
    }

    actual suspend fun fetchIssues(issuesUrl: String): List<IssueResponse> {
        val issuesJson = fetch(issuesUrl.replace("{/number}", ""))
        return decodeFromString<List<IssueResponse>>(issuesJson)
    }

    actual suspend fun fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
        val issueEventsJson = fetch(issueEventsUrl.replace("{/number}", ""))
        return decodeFromString<List<IssueEventResponse>>(issueEventsJson)
    }

}
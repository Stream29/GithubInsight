package io.github.stream29.githubinsight

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

actual class GithubApiProvider actual constructor(
    private val httpClient: HttpClient,
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

    actual suspend fun fetchBase(username: String): ResponseCollection = coroutineScope {
        val userResponse = fetchUser(username)
        val organizationsResponse = fetchOrganizations(userResponse.organizationsUrl)
        val reposResponse = fetchRepositories(userResponse.reposUrl)
        val followersResponse = fetchUsers(userResponse.followersUrl)
        val followingResponse = fetchUsers(userResponse.followingUrl)
        ResponseCollection(
            userResponse,
            organizationsResponse,
            reposResponse,
            followersResponse,
            followingResponse,
        )
    }

    actual suspend fun fetchUser(username: String): UserResponse {
        val userJson = fetch("$baseUserUrl/$username")
        return decodeFromString<UserResponse>(userJson)
    }

    actual suspend fun fetchUsers(usersUrl: String): List<UserResponse> {
        val usersJson = fetch(usersUrl.replace("{/other_user}", ""))
        return decodeFromString<List<UserResponse>>(usersJson)
    }

    actual suspend fun fetchEvents(eventsUrl: String): List<EventResponse> {
        val eventsJson = fetch(eventsUrl.replace("{/privacy}", ""))
        return decodeFromString<List<EventResponse>>(eventsJson)
    }

    actual suspend fun fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
        val orgsJson = fetch(orgsUrl)
        return decodeFromString<List<OrganizationResponse>>(orgsJson)
    }

    actual suspend fun fetchOrgMembers(membersUrl: String): List<UserResponse> {
        val membersJson = fetch(membersUrl.replace("{/member}", ""))
        return decodeFromString<List<UserResponse>>(membersJson)
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
        if (commitsJson.contains("Git Repository is empty")) {
            return decodeFromString<List<CommitResponse>>("[]")
        }
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

    actual suspend fun fetchForks(forksUrl: String): List<RepositoryResponse> {
        val forksJson = fetch(forksUrl)
        return decodeFromString<List<RepositoryResponse>>(forksJson)
    }

    actual suspend fun fetchContributors(contributorsUrl: String): List<UserResponse> {
        val contributorsJson = fetch(contributorsUrl)
        if (contributorsJson.trim().isBlank()) {
            return decodeFromString<List<UserResponse>>("[]")
        }
        return decodeFromString<List<UserResponse>>(contributorsJson)
    }

    actual suspend fun fetchStargazers(stargazersUrl: String): List<UserResponse> {
        val stargazersJson = fetch(stargazersUrl)
        return decodeFromString<List<UserResponse>>(stargazersJson)
    }

    actual suspend fun fetchLanguages(languagesUrl: String): Map<String, Long> {
        val languagesJson = fetch(languagesUrl)
        return decodeFromString<Map<String, Long>>(languagesJson)
    }

}
package io.github.stream29.githubinsight.spider

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

class GithubApiProvider(
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

    suspend fun fetch(url: String): String {
        val responseBody = httpClient.get(url) {
            headers {
                append("Authorization", "Bearer $authToken")
            }
            contentType(ContentType.Application.Json)
        }.bodyAsText()
        return responseBody
    }

    suspend fun fetchBase(login: String): ResponseCollection = coroutineScope {
        val userResponse = fetchUser(login)
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

    suspend fun fetchUser(login: String): UserResponse {
        val userJson = fetch("$baseUserUrl/$login")
        return decodeFromString<UserResponse>(userJson)
    }

    suspend fun fetchUsers(usersUrl: String): List<UserResponse> {
        val usersJson = fetch(usersUrl.replace("{/other_user}", ""))
        return decodeFromString<List<UserResponse>>(usersJson)
    }

    suspend fun fetchEvents(eventsUrl: String): List<EventResponse> {
        val eventsJson = fetch(eventsUrl.replace("{/privacy}", ""))
        return decodeFromString<List<EventResponse>>(eventsJson)
    }

    suspend fun fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
        val orgsJson = fetch(orgsUrl)
        return decodeFromString<List<OrganizationResponse>>(orgsJson)
    }

    suspend fun fetchOrgMembers(membersUrl: String): List<UserResponse> {
        val membersJson = fetch(membersUrl.replace("{/member}", ""))
        return decodeFromString<List<UserResponse>>(membersJson)
    }

    suspend fun fetchRepositories(reposUrl: String): List<RepositoryResponse> {
        val reposJson = fetch(reposUrl)
        return decodeFromString<List<RepositoryResponse>>(reposJson)
    }

    suspend fun fetchReleases(releaseUrl: String): List<ReleaseResponse> {
        val releasesJson = fetch(releaseUrl.replace("{/id}", ""))
        return decodeFromString<List<ReleaseResponse>>(releasesJson)
    }

    suspend fun fetchCommits(commitsUrl: String): List<CommitResponse> {
        val commitsJson = fetch(commitsUrl.replace("{/sha}", ""))
        if (commitsJson.contains("Git Repository is empty")) {
            return decodeFromString<List<CommitResponse>>("[]")
        }
        return decodeFromString<List<CommitResponse>>(commitsJson)
    }

    suspend fun fetchIssues(issuesUrl: String): List<IssueResponse> {
        val issuesJson = fetch(issuesUrl.replace("{/number}", ""))
        return decodeFromString<List<IssueResponse>>(issuesJson)
    }

    suspend fun fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
        val issueEventsJson = fetch(issueEventsUrl.replace("{/number}", ""))
        return decodeFromString<List<IssueEventResponse>>(issueEventsJson)
    }

    suspend fun fetchForks(forksUrl: String): List<RepositoryResponse> {
        val forksJson = fetch(forksUrl)
        return decodeFromString<List<RepositoryResponse>>(forksJson)
    }

    suspend fun fetchContributors(contributorsUrl: String): List<UserResponse> {
        val contributorsJson = fetch(contributorsUrl)
        if (contributorsJson.trim().isBlank()) {
            return decodeFromString<List<UserResponse>>("[]")
        }
        return decodeFromString<List<UserResponse>>(contributorsJson)
    }

    suspend fun fetchStargazers(stargazersUrl: String): List<UserResponse> {
        val stargazersJson = fetch(stargazersUrl)
        return decodeFromString<List<UserResponse>>(stargazersJson)
    }

    suspend fun fetchLanguages(languagesUrl: String): Map<String, Long> {
        val languagesJson = fetch(languagesUrl)
        return decodeFromString<Map<String, Long>>(languagesJson)
    }

}

const val baseUrl = "https://api.github.com"
const val baseUserUrl = "$baseUrl/users"
const val orgUrl = "$baseUrl/orgs"
const val limitedReposCount = 20
package io.github.stream29.githubinsight.spider

import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.httpClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

class GithubApiProvider(
    val authToken: String
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    internal inline fun <reified T> decodeFromString(responseBody: String): T {
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
}

suspend fun GithubApiProvider.getUser(login: String): UserInfo =
    fetchUser(login).run {
        UserInfo(
            company = company,
            blog = blog,
            location = location,
            publicRepos = publicRepos?.toInt() ?: 0,
            publicGists = 0,
            followersAmount = 0,
            followingAmount = 0,
            login = login,
            name = name?: login,
            avatarUrl = avatarUrl,
            bio = bio,
            email = email,
            organizations = listOf("todo"),
            followers = listOf("todo"),
            following = listOf("todo"),
            subscriptions = listOf("todo"),
            repos = listOf("todo"),
        )
    }

suspend fun GithubApiProvider.fetchBase(login: String): ResponseCollection = coroutineScope {
    val userResponse = fetchUser(login)

    val organizationsResponse = async { fetchOrganizations(userResponse.organizationsUrl) }
    val reposResponse = async { fetchRepositories(userResponse.reposUrl) }
    val subscriptionsResponse = async { fetchSubscriptions(userResponse.subscriptionsUrl) }
    val starredResponse = async { fetchStarred(userResponse.starredUrl) }
    val followersResponse = async { fetchUsers(userResponse.followersUrl) }
    val followingResponse = async { fetchUsers(userResponse.followingUrl) }

    ResponseCollection(
        userResponse,
        organizationsResponse.await(),
        reposResponse.await(),
        subscriptionsResponse.await(),
        starredResponse.await(),
        followersResponse.await(),
        followingResponse.await(),
    )
}

suspend fun GithubApiProvider.fetchUser(login: String): UserResponse {
    val userJson = fetch("$baseUserUrl/$login")
    return decodeFromString<UserResponse>(userJson)
}

suspend fun GithubApiProvider.fetchUsers(usersUrl: String): List<UserResponse> {
    val usersJson = fetch(usersUrl.replace("{/other_user}", ""))
    return decodeFromString<List<UserResponse>>(usersJson)
}

suspend fun GithubApiProvider.fetchEvents(eventsUrl: String): List<EventResponse> {
    val eventsJson = fetch(eventsUrl.replace("{/privacy}", ""))
    return decodeFromString<List<EventResponse>>(eventsJson)
}

suspend fun GithubApiProvider.fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
    val orgsJson = fetch(orgsUrl)
    return decodeFromString<List<OrganizationResponse>>(orgsJson)
}

suspend fun GithubApiProvider.fetchOrgMembers(membersUrl: String): List<UserResponse> {
    val membersJson = fetch(membersUrl.replace("{/member}", ""))
    return decodeFromString<List<UserResponse>>(membersJson)
}

suspend fun GithubApiProvider.fetchRepositories(reposUrl: String): List<RepositoryResponse> {
    val reposJson = fetch(reposUrl)
    return decodeFromString<List<RepositoryResponse>>(reposJson)
}

suspend fun GithubApiProvider.fetchRepository(repoUrl: String): RepositoryResponse {
    val repoJson = fetch(repoUrl)
    return decodeFromString<RepositoryResponse>(repoJson)
}

suspend fun GithubApiProvider.fetchSubscriptions(subsUrl: String): List<RepositoryResponse> {
    val subscriptionsJson = fetch(subsUrl)
    return decodeFromString<List<RepositoryResponse>>(subscriptionsJson)
}

suspend fun GithubApiProvider.fetchStarred(starredUrl: String): List<RepositoryResponse> {
    val starredJson = fetch(starredUrl
        .replace("{/owner}", "")
        .replace("{/repo}", ""))
    return decodeFromString<List<RepositoryResponse>>(starredJson)
}

suspend fun GithubApiProvider.fetchReleases(releaseUrl: String): List<ReleaseResponse> {
    val releasesJson = fetch(releaseUrl.replace("{/id}", ""))
    return decodeFromString<List<ReleaseResponse>>(releasesJson)
}

suspend fun GithubApiProvider.fetchCommits(commitsUrl: String): List<CommitResponse> {
    val commitsJson = fetch(commitsUrl.replace("{/sha}", ""))
    if (commitsJson.contains("Git Repository is empty")) {
        return decodeFromString<List<CommitResponse>>("[]")
    }
    return decodeFromString<List<CommitResponse>>(commitsJson)
}

suspend fun GithubApiProvider.fetchIssues(issuesUrl: String): List<IssueResponse> {
    val issuesJson = fetch(issuesUrl.replace("{/number}", ""))
    return decodeFromString<List<IssueResponse>>(issuesJson)
}

suspend fun GithubApiProvider.fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
    val issueEventsJson = fetch(issueEventsUrl.replace("{/number}", ""))
    return decodeFromString<List<IssueEventResponse>>(issueEventsJson)
}

suspend fun GithubApiProvider.fetchForks(forksUrl: String): List<RepositoryResponse> {
    val forksJson = fetch(forksUrl)
    return decodeFromString<List<RepositoryResponse>>(forksJson)
}

suspend fun GithubApiProvider.fetchContributors(contributorsUrl: String): List<UserResponse> {
    val contributorsJson = fetch(contributorsUrl)
    if (contributorsJson.trim().isBlank()) {
        return decodeFromString<List<UserResponse>>("[]")
    }
    return decodeFromString<List<UserResponse>>(contributorsJson)
}

suspend fun GithubApiProvider.fetchStargazers(stargazersUrl: String): List<UserResponse> {
    val stargazersJson = fetch(stargazersUrl)
    return decodeFromString<List<UserResponse>>(stargazersJson)
}

suspend fun GithubApiProvider.fetchLanguages(languagesUrl: String): Map<String, Long> {
    val languagesJson = fetch(languagesUrl)
    return decodeFromString<Map<String, Long>>(languagesJson)
}

const val baseUrl = "https://api.github.com"
const val baseUserUrl = "$baseUrl/users"
const val baseRepoUrl = "$baseUrl/repos"
const val orgUrl = "$baseUrl/orgs"
const val limitedReposCount = 20
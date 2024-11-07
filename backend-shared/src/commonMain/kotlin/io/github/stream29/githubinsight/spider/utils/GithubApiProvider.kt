package io.github.stream29.githubinsight.spider.utils

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.stream29.githubinsight.httpClient
import io.github.stream29.githubinsight.spider.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class GithubApiProvider(
    val authToken: String,
    // FIXME: 需传入mongoDatabase
    mongoDatabase: MongoDatabase,
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val userResponseCollection = mongoDatabase.getCollection<UserResponse>("api_user")
    val jsonCollection = mongoDatabase.getCollection<JsonCollection>("api_json")

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
    userResponseCollection.find(eq("login", login))
        .firstOrNull()
        ?.let { return it }
    val userJson = fetch("$UserUrl/$login")
    val user = decodeFromString<UserResponse>(userJson)
    userResponseCollection.insertOne(user)
    return user
}

suspend fun GithubApiProvider.persistence(jsonUrl: String, blocks: suspend () -> String): String {
    jsonCollection.find(eq("jsonUrl", jsonUrl))
        .firstOrNull()
        ?.let { return it.json }
    val jsonResult = blocks.invoke()
    jsonCollection.insertOne(JsonCollection(jsonUrl, jsonResult))
    return jsonResult
}

suspend fun GithubApiProvider.fetchUsers(usersUrl: String): List<UserResponse> {
    val json = persistence(usersUrl) {
        fetch(usersUrl.replace("{/other_user}", ""))
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchEvents(eventsUrl: String): List<EventResponse> {
    val json = persistence(eventsUrl) {
        fetch(eventsUrl.replace("{/privacy}", ""))
    }
    return decodeFromString<List<EventResponse>>(json)
}

suspend fun GithubApiProvider.fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
    val json = persistence(orgsUrl) {
        fetch(orgsUrl)
    }
    return decodeFromString<List<OrganizationResponse>>(json)
}

suspend fun GithubApiProvider.fetchOrganization(login: String): OrganizationResponse {
    val orgUrl = "$orgUrl/$login"
    val json = persistence(orgUrl) {
        fetch(orgUrl)
    }
    return decodeFromString<OrganizationResponse>(json)
}

suspend fun GithubApiProvider.fetchOrgMembers(membersUrl: String): List<UserResponse> {
    val json = persistence(membersUrl) {
        fetch(membersUrl.replace("{/member}", ""))
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchRepositories(reposUrl: String): List<RepositoryResponse> {
    val json = persistence(reposUrl) {
        fetch(reposUrl)
    }
    return decodeFromString<List<RepositoryResponse>>(json)
}

suspend fun GithubApiProvider.fetchRepository(repoUrl: String): RepositoryResponse {
    val json = persistence(repoUrl) {
        fetch(repoUrl)
    }
    return decodeFromString<RepositoryResponse>(json)
}

suspend fun GithubApiProvider.fetchCollaborators(collaboratorsUrl: String): List<UserResponse> {
    val json = persistence(collaboratorsUrl) {
        fetch(collaboratorsUrl).replace("{/collaborator}", "")
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchSubscribers(subscribersUrl: String): List<UserResponse> {
    val json = persistence(subscribersUrl) {
        fetch(subscribersUrl)
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchSubscriptions(subscriptionsUrl: String): List<RepositoryResponse> {
    val json = persistence(subscriptionsUrl) {
        fetch(subscriptionsUrl)
    }
    return decodeFromString<List<RepositoryResponse>>(json)
}

suspend fun GithubApiProvider.fetchTags(tagsUrl: String): List<TagResponse> {
    val json = persistence(tagsUrl) {
        fetch(tagsUrl)
    }
    return decodeFromString<List<TagResponse>>(json)
}

suspend fun GithubApiProvider.fetchReadme(repoUrl: String): Readme {
    val readmeUrl = "$repoUrl/contents/README.md"
    val json = persistence(readmeUrl) {
        fetch(readmeUrl)
    }
    return decodeFromString<Readme>(json)
}

suspend fun GithubApiProvider.fetchStarred(starredUrl: String): List<RepositoryResponse> {
    val json = persistence(starredUrl) {
        fetch(
            starredUrl
                .replace("{/owner}", "")
                .replace("{/repo}", "")
        )
    }
    return decodeFromString<List<RepositoryResponse>>(json)
}

suspend fun GithubApiProvider.fetchReleases(releaseUrl: String): List<ReleaseResponse> {
    val json = persistence(releaseUrl) {
        fetch(releaseUrl.replace("{/id}", ""))
    }
    return decodeFromString<List<ReleaseResponse>>(json)
}

suspend fun GithubApiProvider.fetchCommits(commitsUrl: String): List<CommitResponse> {
    val json = persistence(commitsUrl) {
        fetch(commitsUrl.replace("{/sha}", ""))
    }
    if (json.contains("Git Repository is empty")) {
        return decodeFromString<List<CommitResponse>>("[]")
    }
    return decodeFromString<List<CommitResponse>>(json)
}

suspend fun GithubApiProvider.fetchIssues(issuesUrl: String): List<IssueResponse> {
    val json = persistence(issuesUrl) {
        fetch(issuesUrl.replace("{/number}", ""))
    }
    return decodeFromString<List<IssueResponse>>(json)
}

suspend fun GithubApiProvider.fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
    val json = persistence(issueEventsUrl) {
        fetch(issueEventsUrl.replace("{/number}", ""))
    }
    return decodeFromString<List<IssueEventResponse>>(json)
}

suspend fun GithubApiProvider.fetchForks(forksUrl: String): List<RepositoryResponse> {
    val json = persistence(forksUrl) {
        fetch(forksUrl)
    }
    return decodeFromString<List<RepositoryResponse>>(json)
}

suspend fun GithubApiProvider.fetchContributors(contributorsUrl: String): List<UserResponse> {
    val json = persistence(contributorsUrl) {
        fetch(contributorsUrl)
    }
    if (json.trim().isBlank()) {
        return decodeFromString<List<UserResponse>>("[]")
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchStargazers(stargazersUrl: String): List<UserResponse> {
    val json = persistence(stargazersUrl) {
        fetch(stargazersUrl)
    }
    return decodeFromString<List<UserResponse>>(json)
}

suspend fun GithubApiProvider.fetchLanguages(languagesUrl: String): Map<String, Int> {
    val json = persistence(languagesUrl) {
        fetch(languagesUrl)
    }
    return decodeFromString<Map<String, Int>>(json)
}

const val baseUrl = "https://api.github.com"
const val UserUrl = "$baseUrl/users"
const val RepoUrl = "$baseUrl/repos"
const val orgUrl = "$baseUrl/orgs"
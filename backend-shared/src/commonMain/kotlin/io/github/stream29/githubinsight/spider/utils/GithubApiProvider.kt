package io.github.stream29.githubinsight.spider.utils

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.stream29.githubinsight.httpClient
import io.github.stream29.githubinsight.spider.*
import io.github.stream29.githubinsight.spider.Exception.FetchException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class GithubApiProvider(
    val authToken: String,
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

suspend fun GithubApiProvider.fetchUser(login: String): UserResponse {
    userResponseCollection.find(eq("login", login))
        .firstOrNull()
        ?.let { return it }
    val userJson = fetch("$UserUrl/$login")
    try {
        val user = decodeFromString<UserResponse>(userJson)
        userResponseCollection.insertOne(user)
        return user
    } catch (e: SerializationException) {
        throw FetchException("fetch user failed.", e)
    }
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
    val jsons = fetchAnyPages(usersUrl.replace("{/other_user}", ""), 1, -1)
    val mergedJson = mergeJsons(jsons)
    return try {
        decodeFromString<List<UserResponse>>(mergedJson)
    } catch (e: SerializationException) {
        decodeFromString<List<UserResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchEvents(eventsUrl: String): List<EventResponse> {
    val newUrl = eventsUrl.replace("{/privacy}", "")
    val json = persistence(newUrl) {
        fetch(newUrl)
    }
    return try {
        decodeFromString<List<EventResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<EventResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
    val json = persistence(orgsUrl) {
        fetch(orgsUrl)
    }
    return try {
        decodeFromString<List<OrganizationResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<OrganizationResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchOrganization(login: String): OrganizationResponse {
    val orgUrl = "$orgUrl/$login"
    val json = persistence(orgUrl) {
        fetch(orgUrl)
    }
    try {
        return decodeFromString<OrganizationResponse>(json)
    } catch (e: SerializationException) {
        throw FetchException("fetch organization failed.", e)
    }
}

suspend fun GithubApiProvider.fetchAnyPages(baseUrl: String, start: Int, pages: Int): List<String> {
    var page = start
    val result: MutableList<String> = mutableListOf()
    while (page < pages || pages == -1) {
        val pageUrl = "$baseUrl?per_page=$perPage&page=$page"
        val json = persistence(pageUrl) {
            fetch(pageUrl)
        }
        if (json == "[]" || (page >= maxPages)) {
            break
        }
        page++
        result.add(json)
    }
    return result
}

fun mergeJsons(jsons: List<String>): String {
    val json = jsons.joinToString(separator = ",") {
        it.removePrefix("[").removeSuffix("]")
    }
    return "[$json]"
}

suspend fun GithubApiProvider.fetchOrgMembers(membersUrl: String): List<UserResponse> {
    val jsons = fetchAnyPages(membersUrl.replace("{/member}", ""), 1, -1)
    val mergedJson = mergeJsons(jsons)
    return try {
        decodeFromString<List<UserResponse>>(mergedJson)
    } catch (e: SerializationException) {
        decodeFromString<List<UserResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchRepositories(reposUrl: String): List<RepositoryResponse> {
    val jsons = fetchAnyPages(reposUrl, 1, -1)
    val mergedJson = mergeJsons(jsons)
    return try {
        decodeFromString<List<RepositoryResponse>>(mergedJson)
    } catch (e: SerializationException) {
        decodeFromString<List<RepositoryResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchRepository(repoUrl: String): RepositoryResponse {
    val json = persistence(repoUrl) {
        fetch(repoUrl)
    }
    try {
        return decodeFromString<RepositoryResponse>(json)
    } catch (e: SerializationException) {
        throw FetchException("fetch repository failed.", e)
    }
}

suspend fun GithubApiProvider.fetchUserResponse(url: String): List<UserResponse> {
    val json = persistence(url) {
        fetch(url)
    }
    return try {
        decodeFromString<List<UserResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<UserResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchCollaborators(collaboratorsUrl: String): List<UserResponse> {
    val newUrl = collaboratorsUrl.replace("{/collaborator}", "")
    return fetchUserResponse(newUrl)
}

suspend fun GithubApiProvider.fetchSubscribers(subscribersUrl: String): List<UserResponse> {
    val jsons = fetchAnyPages(subscribersUrl, 1, -1)
    val mergedJson = mergeJsons(jsons)
    return try {
        decodeFromString<List<UserResponse>>(mergedJson)
    } catch (e: SerializationException) {
        decodeFromString<List<UserResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchSubscriptions(subscriptionsUrl: String): List<RepositoryResponse> {
    return fetchRepositories(subscriptionsUrl)
}

suspend fun GithubApiProvider.fetchTags(tagsUrl: String): List<TagResponse> {
    val json = persistence(tagsUrl) {
        fetch(tagsUrl)
    }
    return try {
        decodeFromString<List<TagResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<TagResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchReadme(repoUrl: String): Readme? {
    val readmeUrl = "$repoUrl/contents/README.md"
    val json = persistence(readmeUrl) {
        fetch(readmeUrl)
    }
    try {
        return decodeFromString<Readme>(json)
    } catch (e: SerializationException) {
        return null
    }
}

suspend fun GithubApiProvider.fetchReleases(releaseUrl: String): List<ReleaseResponse> {
    val newUrl = releaseUrl.replace("{/id}", "")
    val json = persistence(newUrl) {
        fetch(newUrl)
    }
    return try {
        decodeFromString<List<ReleaseResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<ReleaseResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchCommits(commitsUrl: String): List<CommitResponse> {
    val newUrl = commitsUrl.replace("{/sha}", "")
    val json = persistence(newUrl) {
        fetch(newUrl)
    }
    return try {
        decodeFromString<List<CommitResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<CommitResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchIssues(issuesUrl: String): List<IssueResponse> {
    val newUrl = issuesUrl.replace("{/number}", "")
    val json = persistence(newUrl) {
        fetch(newUrl)
    }
    return try {
        decodeFromString<List<IssueResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<IssueResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse> {
    val newUrl = issueEventsUrl.replace("{/number}", "")
    val json = persistence(newUrl) {
        fetch(newUrl)
    }
    return try {
        decodeFromString<List<IssueEventResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<IssueEventResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchForks(forksUrl: String): List<RepositoryResponse> {
    val json = persistence(forksUrl) {
        fetch(forksUrl)
    }
    return try {
        decodeFromString<List<RepositoryResponse>>(json)
    } catch (e: SerializationException) {
        decodeFromString<List<RepositoryResponse>>("[]")
    }
}

suspend fun GithubApiProvider.fetchContributors(contributorsUrl: String): List<UserResponse> {
    return fetchUserResponse(contributorsUrl)
}

suspend fun GithubApiProvider.fetchLanguages(languagesUrl: String): Map<String, Int> {
    val json = persistence(languagesUrl) {
        fetch(languagesUrl)
    }
    return try {
        decodeFromString<Map<String, Int>>(json)
    } catch (e: SerializationException) {
        decodeFromString<Map<String, Int>>("{}")
    }
}

const val baseUrl = "https://api.github.com"
const val UserUrl = "$baseUrl/users"
const val RepoUrl = "$baseUrl/repos"
const val orgUrl = "$baseUrl/orgs"
const val perPage = 100
const val maxPages = 100
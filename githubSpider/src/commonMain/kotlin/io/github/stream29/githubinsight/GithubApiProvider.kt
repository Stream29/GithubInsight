package io.github.stream29.githubinsight

import io.ktor.client.*

expect class GithubApiProvider(
    httpClient: HttpClient,
    authToken: String
) {
    suspend fun fetch(url: String): String
    suspend fun fetchAll(username: String): ResponseCollection
    suspend fun fetchUser(username: String): UserResponse
    suspend fun fetchEvents(eventsUrl: String): String
    suspend fun fetchOrganizations(orgsUrl: String): List<OrganizationResponse>
    suspend fun fetchOrganization(orgUrl: String): OrganizationResponse
    suspend fun fetchRepositories(reposUrl: String): List<RepositoryResponse>
    suspend fun fetchReleases(releaseUrl: String): List<ReleaseResponse>
    suspend fun fetchCommits(commitsUrl: String): List<CommitResponse>
    suspend fun fetchIssues(issuesUrl: String): List<IssueResponse>
    suspend fun fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse>
}

const val baseUrl = "https://api.github.com"
const val baseUserUrl = "$baseUrl/users"
const val limitedReposCount = 20
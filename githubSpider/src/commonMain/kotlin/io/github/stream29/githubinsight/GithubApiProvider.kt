package io.github.stream29.githubinsight

import kotlinx.serialization.DeserializationStrategy

expect class GithubApiProvider(
    authToken: String
) {
    suspend fun <T> fetch(url: String, serializer: DeserializationStrategy<T>): T
    suspend fun fetchAll(username: String)
    suspend fun fetchUser(username: String): UserResponse
    suspend fun fetchRepositories(reposUrl: String): List<RepositoryResponse>
    suspend fun fetchReleases(releaseUrl: String): List<ReleaseResponse>
    suspend fun fetchCommits(commitsUrl: String): List<CommitResponse>
    suspend fun fetchIssues(issuesUrl: String): List<IssueResponse>
    suspend fun fetchIssueEvents(issueEventsUrl: String): List<IssueEventResponse>
}

const val baseUrl = "https://api.github.com"
const val baseUserUrl = "$baseUrl/users"
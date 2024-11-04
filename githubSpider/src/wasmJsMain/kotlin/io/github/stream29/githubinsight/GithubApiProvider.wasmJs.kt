package io.github.stream29.githubinsight

actual class GithubApiProvider actual constructor(
    authToken: String
) {
    actual suspend fun fetch(url: String): String {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchAll(username: String): ResponseCollection {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchUser(username: String): UserResponse {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchOrganizations(orgsUrl: String): List<OrganizationResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun fetchOrganization(orgUrl: String): OrganizationResponse {
        TODO("Not yet implemented")
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
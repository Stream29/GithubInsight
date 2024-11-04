package io.github.stream29.githubinsight

actual class EntityProcessor actual constructor() {
    actual fun process(responseCollection: ResponseCollection): User {
        TODO("Not yet implemented")
    }

    actual fun toUser(userResponse: UserResponse): User {
        TODO("Not yet implemented")
    }

    actual fun toRepositories(repositoriesResponse: List<RepositoryResponse>): List<Repository> {
        TODO("Not yet implemented")
    }

    actual fun toOrganizations(organizationsResponse: List<OrganizationResponse>): List<Organization> {
        TODO("Not yet implemented")
    }

    actual fun toReleases(releasesResponse: List<ReleaseResponse>): List<Release> {
        TODO("Not yet implemented")
    }

    actual fun toAssets(assetsResponse: AssetsResponse): Assets {
        TODO("Not yet implemented")
    }

    actual fun toCommits(commitsResponse: CommitResponse): List<Commit> {
        TODO("Not yet implemented")
    }

    actual fun toIssues(issuesResponse: IssueResponse): List<Issue> {
        TODO("Not yet implemented")
    }

    actual fun toIssueEvents(issueEventsResponse: IssueEventResponse): List<IssueEvent> {
        TODO("Not yet implemented")
    }
}
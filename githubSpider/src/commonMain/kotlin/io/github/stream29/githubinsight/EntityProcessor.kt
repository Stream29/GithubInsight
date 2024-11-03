package io.github.stream29.githubinsight

expect class EntityProcessor() {
    fun process()
    fun toUser(userResponse: UserResponse): User
    fun toRepositories(repositoriesResponse: List<RepositoryResponse>): List<Repository>
    fun toOrganizations(organizationsResponse: List<OrganizationResponse>): List<Organization>
    fun toReleases(releasesResponse: List<ReleaseResponse>): List<Release>
    fun toAssets(assetsResponse: AssetsResponse): Assets
    fun toCommits(commitsResponse: CommitResponse): List<Commit>
    fun toIssues(issuesResponse: IssueResponse): List<Issue>
    fun toIssueEvents(issueEventsResponse: IssueEventResponse): List<IssueEvent>
}
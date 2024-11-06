package io.github.stream29.githubinsight

expect class EntityProcessor() {
    fun toUser(responseCollection: ResponseCollection): User
    fun toUsersName(usersResponse: List<UserResponse>): List<String>
    fun toEvents(eventsResponse: List<EventResponse>): List<Event>
    fun toRepositoriesFullName(repositoriesResponse: List<RepositoryResponse>): List<String>
    fun toRepository(
        repositoryResponse: RepositoryResponse,
        forks: List<RepositoryResponse>,
        contributors: List<UserResponse>,
        stargazers: List<UserResponse>,
        languages: Map<String, Long>
    ): Repository
    fun toOrganizationsName(organizationsResponse: List<OrganizationResponse>): List<String>
    fun toOrganization(
        organizationResponse: OrganizationResponse,
        members: List<UserResponse>
    ): Organization
    fun toReleases(releasesResponse: List<ReleaseResponse>): List<Release>
    fun toAssets(assetsResponse: List<AssetResponse>): List<Asset>
    fun toCommits(commitsResponse: List<CommitResponse>): List<Commit>
    fun toIssues(issuesResponse: List<IssueResponse>): List<Issue>
    fun toIssueEvents(issueEventsResponse: List<IssueEventResponse>): List<IssueEvent>
}
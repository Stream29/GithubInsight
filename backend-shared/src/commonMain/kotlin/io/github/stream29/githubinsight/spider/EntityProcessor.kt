package io.github.stream29.githubinsight

class EntityProcessor {
    companion object {
        fun toUser(responseCollection: ResponseCollection): User {
            return User(
                responseCollection.userResponse.id,
                responseCollection.userResponse.login,
                responseCollection.userResponse.avatarUrl,
                toUsersName(responseCollection.followersResponse),
                toUsersName(responseCollection.followingResponse),
                toOrganizationsName(responseCollection.orgsResponse),
                toRepositoriesFullName(responseCollection.reposResponse),
                responseCollection.userResponse.siteAdmin,
                responseCollection.userResponse.name,
                responseCollection.userResponse.company,
                responseCollection.userResponse.blog,
                responseCollection.userResponse.location,
                responseCollection.userResponse.email,
                responseCollection.userResponse.hireable,
                responseCollection.userResponse.bio,
                responseCollection.userResponse.twitterUsername,
                responseCollection.userResponse.publicRepos,
                responseCollection.userResponse.createdAt,
                responseCollection.userResponse.updatedAt,
                responseCollection.userResponse.diskUsage,
                responseCollection.userResponse.collaborators,
            )
        }

        private fun toUsersName(usersResponse: List<UserResponse>): List<String> {
            return usersResponse
                .asSequence()
                .map { it.login }
                .toList()
        }

        fun toEvents(eventsResponse: List<EventResponse>): List<Event> {
            return eventsResponse
                .asSequence()
                .map {
                    Event(
                        it.id,
                        it.type,
                        it.actor.login,
                        it.repo.fullName,
                    )
                }.toList()
        }

        private fun toRepositoriesFullName(repositoriesResponse: List<RepositoryResponse>): List<String> {
            return repositoriesResponse
                .asSequence()
                .sortedByDescending { it.stargazersCount }
                .map { it.fullName }
                .toList()
        }

        fun toRepository(
            repositoryResponse: RepositoryResponse,
            forks: List<RepositoryResponse>,
            contributors: List<UserResponse>,
            stargazers: List<UserResponse>,
            languages: Map<String, Long>
        ): Repository {
            return Repository(
                repositoryResponse.id,
                repositoryResponse.name,
                repositoryResponse.fullName,
                repositoryResponse.owner?.login,
                repositoryResponse.private,
                repositoryResponse.description,
                repositoryResponse.fork,
                toRepositoriesFullName(forks),
                repositoryResponse.forksCount,
                toUsersName(contributors),
                repositoryResponse.openIssuesCount,
                languages,
                toUsersName(stargazers),
                repositoryResponse.stargazersCount,
                repositoryResponse.watchersCount,
                repositoryResponse.size,
                repositoryResponse.topics,
                repositoryResponse.hasIssues,
                repositoryResponse.hasProjects,
                repositoryResponse.hasWiki,
                repositoryResponse.hasPages,
                repositoryResponse.hasDownloads,
                repositoryResponse.hasDiscussions,
                repositoryResponse.archived,
                repositoryResponse.disabled,
                repositoryResponse.visibility,
                repositoryResponse.pushedAt,
                repositoryResponse.createdAt,
                repositoryResponse.updatedAt,
                repositoryResponse.permissions,
                repositoryResponse.allowForking,
                repositoryResponse.subscribersCount,
                repositoryResponse.networkCount,
                repositoryResponse.license,
            )
        }

        private fun toOrganizationsName(organizationsResponse: List<OrganizationResponse>): List<String> {
            return organizationsResponse
                .asSequence()
                .map { it.login }
                .toList()
        }

        fun toOrganization(organizationResponse: OrganizationResponse,
                           members: List<UserResponse>): Organization {
            return Organization(
                organizationResponse.id,
                organizationResponse.login,
                members.asSequence()
                    .map { it.login }
                    .toList()
            )
        }

        fun toReleases(releasesResponse: List<ReleaseResponse>): List<Release> {
            return releasesResponse
                .asSequence()
                .map {
                    Release(
                        it.id,
                        it.author.login,
                        it.name,
                        it.prerelease,
                        it.createdAt,
                        it.publishedAt,
                        toAssets(it.assets),
                        it.body,
                    )
                }.toList()
        }

        private fun toAssets(assetsResponse: List<AssetResponse>): List<Asset> {
            return assetsResponse
                .asSequence()
                .map {
                    Asset(
                        it.id,
                        it.uploader.login,
                        it.state,
                        it.size,
                        it.downloadCount,
                        it.createdAt,
                        it.updatedAt,
                    )
                }.toList()
        }

        fun toCommits(commitsResponse: List<CommitResponse>): List<Commit> {
            return commitsResponse
                .asSequence()
                .map {
                    Commit(
                        it.sha,
                        it.message,
                        it.author?.login,
                        it.committer?.login,
                    )
                }.toList()
        }

        fun toIssues(issuesResponse: List<IssueResponse>): List<Issue> {
            return issuesResponse
                .asSequence()
                .map {
                    Issue(
                        it.id,
                        it.title,
                        it.user.login,
                    )
                }.toList()
        }

        fun toIssueEvents(issueEventsResponse: List<IssueEventResponse>): List<IssueEvent> {
            return issueEventsResponse
                .asSequence()
                .map {
                    IssueEvent(
                        it.id,
                        it.actor.login,
                        it.event,
                    )
                }.toList()
        }
    }
}
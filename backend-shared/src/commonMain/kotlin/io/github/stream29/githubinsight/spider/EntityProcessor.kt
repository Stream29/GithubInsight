package io.github.stream29.githubinsight.spider

import io.github.stream29.githubinsight.entities.Repository
import io.github.stream29.githubinsight.entities.UserCommit
import io.github.stream29.githubinsight.entities.UserInfo

class EntityProcessor {
    companion object {
        fun toUserInfo(responseCollection: ResponseCollection): UserInfo {
            return UserInfo(
                login = responseCollection.userResponse.login,
                name = responseCollection.userResponse.name ?: "",
                avatarUrl = responseCollection.userResponse.avatarUrl,
                bio = responseCollection.userResponse.bio,
                email = responseCollection.userResponse.email,
                organizations = toOrganizationsName(responseCollection.orgsResponse),
                followers = toUsersName(responseCollection.followersResponse),
                following = toUsersName(responseCollection.followingResponse),
                subscriptions = toRepositoriesFullName(responseCollection.subscriptionsResponse),
                repos = toRepositoriesFullName(responseCollection.reposResponse),
                company = responseCollection.userResponse.company,
                blog = responseCollection.userResponse.blog,
                location = responseCollection.userResponse.location,
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
            contributors: List<UserResponse>,
            languages: Map<String, Int>,
            subscribers: List<UserResponse>,
            collaborators: List<UserResponse>,
            commits: List<CommitResponse>,
            tags: List<TagResponse>,
            readme: Readme,
        ): Repository {
            return Repository(
                name = repositoryResponse.name,
                description = repositoryResponse.description,
                collaborators = toUsersName(collaborators),
                tags = toTagsName(tags),
                languages = languages,
                contributors = toUsersName(contributors),
                subscribers = toUsersName(subscribers),
                commits = toUserCommit(commits),
                starsCount = repositoryResponse.stargazersCount.toInt(),
                watchersCount = repositoryResponse.watchersCount.toInt(),
                forksCount = repositoryResponse.forksCount.toInt(),
                topics = repositoryResponse.topics,
                readme = readme.content,
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

        private fun toTagsName(tagsResponse: List<TagResponse>): List<String> {
            return tagsResponse.asSequence()
                .map { it.name }
                .toList()
        }

        private fun toUserCommit(commitsResponse: List<CommitResponse>): List<UserCommit> {
            return commitsResponse.asSequence()
                .map {
                    UserCommit(
                        author = it.author?.login ?: "",
                        message = it.message ?: "",
                    )
                }.toList()
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
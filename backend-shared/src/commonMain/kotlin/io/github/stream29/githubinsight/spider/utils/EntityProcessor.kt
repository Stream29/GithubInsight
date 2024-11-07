package io.github.stream29.githubinsight.spider.utils

import io.github.stream29.githubinsight.common.entities.Organization
import io.github.stream29.githubinsight.common.entities.Repository
import io.github.stream29.githubinsight.common.entities.UserCommit
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.spider.*

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
            readmeContent: String,
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
                readme = readmeContent,
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
                organizationResponse.login,
                members.asSequence()
                    .map { it.login }
                    .toList(),
                organizationResponse.description,
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
    }
}
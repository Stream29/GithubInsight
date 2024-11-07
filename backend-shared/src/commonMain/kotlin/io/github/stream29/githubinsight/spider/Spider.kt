package io.github.stream29.githubinsight.spider

import io.github.stream29.githubinsight.common.entities.Organization
import io.github.stream29.githubinsight.common.entities.Repository
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.spider.utils.*
import kotlinx.coroutines.coroutineScope


class Spider (
    private val balancingApiProvider: BalancingApiProvider
) {
    suspend fun getUserInfo(login: String): UserInfo {
        val userResponse = balancingApiProvider.execute { a -> a.fetchUser(login) }
        val organizationsResponse = balancingApiProvider.execute { a -> a.fetchOrganizations(userResponse.organizationsUrl) }
        val reposResponse = balancingApiProvider.execute { a -> a.fetchRepositories(userResponse.reposUrl) }
        val subscriptionsResponse = balancingApiProvider.execute { a -> a.fetchSubscriptions(userResponse.subscriptionsUrl) }
        val starredResponse = balancingApiProvider.execute { a -> a.fetchStarred(userResponse.starredUrl) }
        val followersResponse = balancingApiProvider.execute { a -> a.fetchUsers(userResponse.followersUrl) }
        val followingResponse = balancingApiProvider.execute { a -> a.fetchUsers(userResponse.followingUrl) }
        return EntityProcessor.toUserInfo(
            ResponseCollection(
                userResponse = userResponse,
                orgsResponse = organizationsResponse,
                reposResponse = reposResponse,
                subscriptionsResponse = subscriptionsResponse,
                starredresponse = starredResponse,
                followersResponse = followersResponse,
                followingResponse = followingResponse,
            )
        )
    }

    // get a repository all information
    suspend fun getRepository(repoFullName: String): Repository {
        val repositoryResponse = balancingApiProvider.execute { a ->
            a.fetchRepository("$RepoUrl/$repoFullName")
        }
        val contributors = balancingApiProvider.execute { a -> a.fetchContributors(repositoryResponse.contributorsUrl) }
        val languages = balancingApiProvider.execute { a -> a.fetchLanguages(repositoryResponse.languagesUrl) }
        val subscribers = balancingApiProvider.execute { a -> a.fetchSubscribers(repositoryResponse.subscribersUrl) }
        val collaborators = balancingApiProvider.execute { a -> a.fetchCollaborators(repositoryResponse.collaboratorsUrl) }
        val commits = balancingApiProvider.execute { a -> a.fetchCommits(repositoryResponse.commitsUrl) }
        val tags = balancingApiProvider.execute { a -> a.fetchTags(repositoryResponse.tagsUrl) }
        val readme = balancingApiProvider.execute { a -> a.fetchReadme(repositoryResponse.url) }
        return EntityProcessor.toRepository(
            repositoryResponse = repositoryResponse,
            contributors = contributors,
            languages = languages,
            subscribers = subscribers,
            collaborators = collaborators,
            commits = commits,
            tags = tags,
            readmeContent = readme?.content ?: "",
        )
    }

    // get organization information
    suspend fun getOrganization(login: String): Organization = coroutineScope {
        val organizationResponse = balancingApiProvider.execute { a -> a.fetchOrganization(login) }
        val organizationMembers = balancingApiProvider.execute { a -> a.fetchOrgMembers(organizationResponse.membersUrl) }
        EntityProcessor.toOrganization(
            organizationResponse = organizationResponse,
            members = organizationMembers,
        )
    }
}
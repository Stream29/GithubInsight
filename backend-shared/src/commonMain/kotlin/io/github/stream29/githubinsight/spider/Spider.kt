package io.github.stream29.githubinsight.spider

import io.github.stream29.githubinsight.common.entities.Organization
import io.github.stream29.githubinsight.common.entities.Repository
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.spider.utils.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class Spider(
    private val balancingApiProvider: BalancingApiProvider
) {
    suspend fun getUserInfo(login: String): UserInfo = coroutineScope {
        val userResponse = balancingApiProvider.execute { a -> a.fetchUser(login) }
        val organizationsResponse =
            async { balancingApiProvider.execute { a -> a.fetchOrganizations(userResponse.organizationsUrl) } }
        val reposResponse = async { balancingApiProvider.execute { a -> a.fetchRepositories(userResponse.reposUrl) } }
        val subscriptionsResponse =
            async { balancingApiProvider.execute { a -> a.fetchSubscriptions(userResponse.subscriptionsUrl) } }
        val followersResponse = async { balancingApiProvider.execute { a -> a.fetchUsers(userResponse.followersUrl) } }
        val followingResponse = async { balancingApiProvider.execute { a -> a.fetchUsers(userResponse.followingUrl) } }
        return@coroutineScope EntityProcessor.toUserInfo(
            ResponseCollection(
                userResponse = userResponse,
                orgsResponse = organizationsResponse.await(),
                reposResponse = reposResponse.await(),
                subscriptionsResponse = subscriptionsResponse.await(),
                followersResponse = followersResponse.await(),
                followingResponse = followingResponse.await(),
            )
        )
    }

    // get a repository all information
    suspend fun getRepository(repoFullName: String): Repository = coroutineScope {
        val repositoryResponse = balancingApiProvider.execute { a ->
            a.fetchRepository("$RepoUrl/$repoFullName")
        }
        val contributors =
            async { balancingApiProvider.execute { a -> a.fetchContributors(repositoryResponse.contributorsUrl) } }
        val languages =
            async { balancingApiProvider.execute { a -> a.fetchLanguages(repositoryResponse.languagesUrl) } }
        val subscribers =
            async { balancingApiProvider.execute { a -> a.fetchSubscribers(repositoryResponse.subscribersUrl) } }
        val collaborators =
            async { balancingApiProvider.execute { a -> a.fetchCollaborators(repositoryResponse.collaboratorsUrl) } }
        val commits = async { balancingApiProvider.execute { a -> a.fetchCommits(repositoryResponse.commitsUrl) } }
        val tags = async { balancingApiProvider.execute { a -> a.fetchTags(repositoryResponse.tagsUrl) } }
        val readme = async { balancingApiProvider.execute { a -> a.fetchReadme(repositoryResponse.url) } }
        return@coroutineScope EntityProcessor.toRepository(
            repositoryResponse = repositoryResponse,
            contributors = contributors.await(),
            languages = languages.await(),
            subscribers = subscribers.await(),
            collaborators = collaborators.await(),
            commits = commits.await(),
            tags = tags.await(),
            readmeContent = readme.await()?.content ?: "",
        )
    }


    // get organization information
    suspend fun getOrganization(login: String): Organization = coroutineScope {
        val organizationResponse = balancingApiProvider.execute { a -> a.fetchOrganization(login) }
        val organizationMembers =
            balancingApiProvider.execute { a -> a.fetchOrgMembers(organizationResponse.membersUrl) }
        EntityProcessor.toOrganization(
            organizationResponse = organizationResponse,
            members = organizationMembers,
        )
    }
}
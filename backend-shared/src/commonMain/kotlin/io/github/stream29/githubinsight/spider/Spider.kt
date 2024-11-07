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
        val responseCollection = balancingApiProvider.execute { a ->
            a.fetchBase(login)
        }
        return EntityProcessor.toUserInfo(responseCollection)
    }

    // get a repository all information
    suspend fun getRepository(repoFullName: String): Repository = coroutineScope {
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
        EntityProcessor.toRepository(
            repositoryResponse,
            contributors,
            languages,
            subscribers,
            collaborators,
            commits,
            tags,
            readme?.content ?: "",
        )
    }

    // get organization information
    suspend fun getOrganization(login: String): Organization = coroutineScope {
        val organizationResponse = balancingApiProvider.execute { a -> a.fetchOrganization(login) }
        val organizationMembers = balancingApiProvider.execute { a -> a.fetchOrgMembers(organizationResponse.membersUrl) }
        EntityProcessor.toOrganization(
            organizationResponse,
            organizationMembers,
        )
    }
}
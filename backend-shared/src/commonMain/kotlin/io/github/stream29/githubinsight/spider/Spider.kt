package io.github.stream29.githubinsight.spider

import io.github.stream29.githubinsight.common.entities.Repository
import io.github.stream29.githubinsight.common.entities.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class Spider (
    val balancingApiProvider: BalancingApiProvider
) {
    suspend fun getUserInfo(login: String): UserInfo {
        val responseCollection = balancingApiProvider.execute { a ->
            a.fetchBase(login)
        }
        return EntityProcessor.toUserInfo(responseCollection)
    }

    // get more info: events
    suspend fun getUserEvents(login: String): List<Event> {
        val userResponse = balancingApiProvider.execute { a ->
            a.fetchUser(login)
        }
        val eventsResponse = balancingApiProvider.execute { a ->
            a.fetchEvents(userResponse.eventsUrl)
        }
        return EntityProcessor.toEvents(eventsResponse)
    }

    // get a repository all information
    suspend fun getRepository(repoFullName: String): Repository = coroutineScope {
        val repositoryResponse = balancingApiProvider.execute { a ->
            a.fetchRepository("$baseRepoUrl/$repoFullName")
        }
        val contributors = async { balancingApiProvider.execute { a -> a.fetchContributors(repositoryResponse.contributorsUrl) } }
        val languages = async { balancingApiProvider.execute { a -> a.fetchLanguages(repositoryResponse.languagesUrl) } }
        val subscribers = async { balancingApiProvider.execute { a -> a.fetchSubscribers(repositoryResponse.subscribersUrl) } }
        val collaborators = async { balancingApiProvider.execute { a -> a.fetchCollaborators(repositoryResponse.collaboratorsUrl) } }
        val commits = async { balancingApiProvider.execute { a -> a.fetchCommits(repositoryResponse.commitsUrl) } }
        val tags = async { balancingApiProvider.execute { a -> a.fetchTags(repositoryResponse.tagsUrl) } }
        val readme = async { balancingApiProvider.execute { a -> a.fetchReadme(repositoryResponse.url) } }
        EntityProcessor.toRepository(
            repositoryResponse,
            contributors.await(),
            languages.await(),
            subscribers.await(),
            collaborators.await(),
            commits.await(),
            tags.await(),
            readme.await(),
        )
    }
}
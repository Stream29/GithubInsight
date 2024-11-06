package io.github.stream29.githubinsight.spider

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class Spider (
    val balancingApiProvider: BalancingApiProvider
) {
    suspend fun getUserInfo(login: String): User {
        val responseCollection = balancingApiProvider.execute { a ->
            a.fetchBase(login)
        }
        return EntityProcessor.toUser(responseCollection)
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
        val forks = async { balancingApiProvider.execute { a -> a.fetchForks(repositoryResponse.forksUrl) } }
        val contributors = async { balancingApiProvider.execute { a -> a.fetchContributors(repositoryResponse.contributorsUrl) } }
        val stargazers = async { balancingApiProvider.execute { a -> a.fetchStargazers(repositoryResponse.stargazersUrl) } }
        val languages = async { balancingApiProvider.execute { a -> a.fetchLanguages(repositoryResponse.languagesUrl) } }
        EntityProcessor.toRepository(
            repositoryResponse,
            forks.await(),
            contributors.await(),
            stargazers.await(),
            languages.await(),
        )
    }
}
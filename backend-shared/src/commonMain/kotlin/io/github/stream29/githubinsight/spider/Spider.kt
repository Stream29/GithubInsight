package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.spider.BalancingApiProvider
import io.github.stream29.githubinsight.spider.Repository

class Spider (
    val balancingApiProvider: BalancingApiProvider
) {
    suspend fun getBaseUserInformation(login: String): User {
        val userResponse = balancingApiProvider.execute { a ->
            a.fetchBase(login)
        }
        return EntityProcessor.toUser(userResponse)
    }

    suspend fun getRepositories(login: String): List<Repository> {
        TODO("Not yet implemented")
    }
}
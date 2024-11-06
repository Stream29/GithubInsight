package io.github.stream29.githubinsight.analysis

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoCollection
import io.github.stream29.githubinsight.entities.ContributionVector
import io.github.stream29.githubinsight.entities.Estimated
import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.entities.UserResult
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.githubinsight.spider.getUser
import io.github.stream29.langchain4kt.core.ChatApiProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull

data class Analyser(
    val userInfoCollection: MongoCollection<UserInfo>,
    val userResultCollection: MongoCollection<UserResult>,
    val chatApiProvider: ChatApiProvider<*>,
    val githubApiProvider: GithubApiProvider
)

suspend fun Analyser.analyseUser(userLogin: String): UserResult = coroutineScope {
    userResultCollection.find(eq("login", userLogin))
        .firstOrNull()
        ?.let { return@coroutineScope it }

    val userInfo = githubApiProvider.getUser(userLogin)
    withContext(Dispatchers.IO) {
        val nation = async { analyseNation(userInfo) }
        val talentRank = async { analyseTalentRank(userInfo) }
        val result = UserResult(
            login = userLogin,
            talentRank = talentRank.await(),
            nation = nation.await()
        )
        launch {
            userResultCollection.insertOne(result)
        }
        result
    }
}

suspend fun Analyser.analyseTalentRank(userInfo: UserInfo): ContributionVector<String> = coroutineScope {
    //TODO
    ContributionVector(mapOf("Java" to "完全不会，杂鱼"))
}

suspend fun Analyser.analyseNation(userInfo: UserInfo): Estimated<String> = coroutineScope {
    //TODO
    Estimated(0, "China")
}
package io.github.stream29.githubinsight.analysis

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoCollection
import io.github.stream29.githubinsight.entities.Estimated
import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.entities.UserResult
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.langchain4kt.core.ChatApiProvider
import kotlinx.coroutines.coroutineScope
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
    //TODO
    UserResult(
        login = userLogin,
        talentRank = ContributionVector(mapOf("Java" to "他一点都不懂")),
        nation = Estimated(0, "真的不知道啊嘤嘤嘤")
    )
}
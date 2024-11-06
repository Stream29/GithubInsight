package io.github.stream29.githubinsight.analysis

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.stream29.githubinsight.entities.ContributionVector
import io.github.stream29.githubinsight.entities.Estimated
import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.entities.UserResult
import io.github.stream29.githubinsight.spider.Spider
import io.github.stream29.langchain4kt.core.ChatApiProvider
import io.github.stream29.langchain4kt.core.asRespondent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull

data class Analyser(
    val database: MongoDatabase,
    val chatApiProvider: ChatApiProvider<*>,
    val githubSpider: Spider
) {
    val userResultCollection = database.getCollection<UserResult>("user_result")
}

suspend fun Analyser.analyseUser(userLogin: String): UserResult = coroutineScope {
    userResultCollection.find(eq("login", userLogin))
        .firstOrNull()
        ?.let { return@coroutineScope it }

    val userInfo = githubSpider.getUserInfo(userLogin)
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
    if (userInfo.location != null) {
        return@coroutineScope inferNationFrom(userInfo.location)
    }
    Estimated(0, "China")
}

suspend fun Analyser.inferNationFrom(location: String): Estimated<String> = coroutineScope {
    val respondent = chatApiProvider.asRespondent()
    val nationInferred =
        respondent.chat(
            """
            =====以下为问答示例=====
            问：已知一个人的位置是Beijing, China，请问他在哪个国家？
            答：中国
            =====以上为问答示例=====
            你应当遵循问答示例的格式进行问答。回答的内容应当是一个国家名。不准输出其他内容。
            已知一个人的位置是$location，请问他在哪个国家？
        """.trimIndent()
        )
    val belief = respondent.chat(
        """
        =====以下为问答示例=====
        问：从位置信息California推断所在国家为美国的置信度是多少？
        答：100
        问：从位置信息Countryside推断所在国家为印度的置信度是多少？
        答：0
        =====以上为问答示例=====
        你应当遵循问答示例的格式进行问答。回答的内容应当是一个0到100之间的整数，表示你对这个推断的置信度。不准输出其他内容。
        从位置信息${location}推断所在国家为${nationInferred}的置信度是多少？
    """.trimIndent()
    )
    withRetry(10) {
        Estimated(belief.toInt(), nationInferred)
    }
}

fun <T> withRetry(count: Int, block: () -> T): T {
    var currentCount = 0
    while (true) {
        try {
            return block()
        } catch (e: Exception) {
            if (currentCount >= count) {
                throw RuntimeException("Retry failed.", e)
            }
            currentCount++
        }
    }
}
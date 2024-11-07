package io.github.stream29.githubinsight.analysis

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.stream29.githubinsight.common.entities.*
import io.github.stream29.githubinsight.spider.Spider
import io.github.stream29.langchain4kt.core.ChatApiProvider
import io.github.stream29.langchain4kt.core.asRespondent
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*

data class Analyser(
    val database: MongoDatabase,
    val chatApiProvider: ChatApiProvider<*>,
    val githubSpider: Spider
) {
    val userResultCollection = database.getCollection<UserResult>("user_result")
    val repoResultCollection = database.getCollection<RepoResult>("repo_result")
}

suspend fun Analyser.analyseUser(userLogin: String): UserResult = coroutineScope {
    userResultCollection.find(eq("login", userLogin))
        .firstOrNull()
        ?.let { return@coroutineScope it }

    val userInfo = githubSpider.getUserInfo(userLogin)
    val nation = async { analyseNation(userInfo) }
    val talentRank = async { analyseTalentRank(userInfo) }
    val result = UserResult(
        login = userLogin,
        talentRank = talentRank.await() ?: ContributionVector(emptyMap()),
        nation = nation.await()
    )
    launch {
        userResultCollection.insertOne(result)
    }
    result
}

suspend fun Analyser.analyseTalentRank(userInfo: UserInfo): ContributionVector = coroutineScope {
    val repositories = userInfo.repos.map {
        async { analyseRepo(it) }
    }.awaitAll()
    val techSet =
        repositories.asSequence().filterNotNull().map { it.techValue }.fold(mutableSetOf<String>()) { acc, map ->
            acc.addAll(map.keys); acc
        }
    val respondent = chatApiProvider.asRespondent()
    val techValue = techSet.associateWith { tech ->
        val relevantRepos = repositories.asSequence().filterNotNull()
            .filter { it.techValue.containsKey(tech) }
            .filter { it.contributionTotal > 0 }
            .filter { it.contributeMap.contains(userInfo.login) }
        val info = relevantRepos.joinToString("\n") {
            val percentage = (it.contributeMap[userInfo.login]!! * 100.toDouble() / it.contributionTotal).formatted()
            """
                在${it.name}项目（star数为${it.starsCount}），${it.techValue[tech]}，贡献占比为$percentage%。
            """.trimIndent()
        }
        val evaluation = respondent.chat(
            """
            =====以下为问答示例=====
            问：
            在githubInsight项目（star数为100），参与了后端分析逻辑的开发，贡献占比为30%。
            以上为一个github用户在kotlin方面的仓库贡献履历。请问他在这方面技术水平如何？
            答：熟练Kotlin，尤其是后端开发。
            =====以上为问答示例=====
            你应当遵循问答示例的格式进行问答。
            回答的内容应该是一个短句，指出用户的技术水平。不准输出其他内容。
            
            $info
            以上为一个github用户在${tech}方面的仓库贡献履历。请问他在这方面技术水平如何？
        """.trimIndent()
        )
        val score = withRetry(3) {
            respondent.chat(
                """
                    =====以下为问答示例=====
                    问：
                    熟练Kotlin，尤其是后端开发。
                    以上是对一个github用户在Kotlin方面的技术水平的评价。
                    请问这个用户在Kotlin方面的技术水平可以打多少分？标准为：会使用-20分；熟练使用-40分；开源贡献很多-60分；开源贡献影响力大-80分；顶级开源开发者-100分；你应当综合考虑多方面因素，在这个基础上进行增减。
                    答：40
                    =====以上为问答示例=====
                    你应当遵循问答示例的格式进行问答。
                    回答的内容应当是一个0到100之间的整数，表示用户的技术水平，最好不要是整十的数字，而是56之类的普通数字。不准输出其他内容。
                    
                    $evaluation
                    以上是对一个github用户在${tech}方面的技术水平的评价。
                    请问这个用户在${tech}方面的技术水平可以打多少分？标准为：会使用-20分；熟练使用-40分；开源贡献很多-60分；开源贡献影响力大-80分；顶级开源开发者-100分；你应当综合考虑多方面因素，在这个基础上进行增减。
                """.trimIndent()
            ).parseInt()
        }
        evaluation to score
    }
    ContributionVector(techValue.filter { it.value.second > 0 })
}

suspend fun Analyser.analyseRepo(repo: String): RepoResult? = coroutineScope {
    repoResultCollection.find(eq("name", repo))
        .firstOrNull()
        ?.let { return@coroutineScope it }
    val repoInfo = githubSpider.getRepository(repo) ?: return@coroutineScope null
    val contributors = repoInfo.contributors

    val readme = repoInfo.readme
    val result =
        if (readme != null && readme.length > 10) RepoResult(
            name = repo,
            techValue = analyseTechValue(repoInfo, readme),
            contributeMap = contributors.associateWith { contributor ->
                repoInfo.commits.count { it.author == contributor }
            },
            contributionTotal = repoInfo.commits.size,
            starsCount = repoInfo.starsCount
        )
        else RepoResult(
            name = repo,
            techValue = emptyMap(),
            contributeMap = emptyMap(),
            0,
            0
        )
    launch {
        repoResultCollection.insertOne(result)
    }
    result
}

suspend fun Analyser.analyseTechValue(repoInfo: Repository, readme: String): Map<String, String> = coroutineScope {
    val respondent = chatApiProvider.asRespondent()
    val languages = repoInfo.languages
    val languageSum = languages.values.sum()
    val topics = repoInfo.topics

    val languageValue = languages.map { (language, count) ->
        val percentage = (count.toDouble() * 100 / languageSum).formatted()
        async {
            language to respondent.chat(
                """
                =====以下为问答示例=====
                问：
                本项目后端基于Spring Boot，前端基于Vue.js，数据库使用了MySQL，实现了一个在线商城系统。
                用户可以在网站上浏览商品、下单购买、查看订单。
                以上为OnlineMall项目的ReadMe。
                这个项目使用了Java语言，占比为30%，得到了100个star，请问这个项目在Java方面的技术价值如何？
                答：后端部分使用了Java，进行了应用开发。
                =====以上为问答示例=====
                你应当遵循问答示例的格式进行问答。
                回答的内容应该是一个短句，包含“这个项目的哪些部分使用了该语言”以及“该项目的重要性”两个部分。不准输出其他内容。
                项目的重要性应该是“应用开发”、“系统设计”、“数据分析”、“算法研究”等。
                
                $readme
                以上为${repoInfo.name}项目的ReadMe。
                这个项目，使用了${language}语言，占比为${percentage}%，得到了${repoInfo.starsCount}个star。
                请问这个项目在${language}方面的技术价值如何？
            """.trimIndent()
            )
        }
    }.awaitAll().toMap()
    val topicValue = topics.map { topic ->
        async {
            topic to respondent.chat(
                """
                =====以下为问答示例=====
                问：
                Poac (pronounced as /pəʊək/) is a package manager and build system for C++ users, inspired by Cargo for Rust. 
                Poac is designed as a structure-oriented build system, which means that as long as you follow Poac's designated project structure, 
                you almost do not need configurations, much less a language to build, unlike CMake. If you do not like writing a bunch of configurations to build your project, 
                Poac might be best suited. Currently, the supported project structure can be known by looking at this repository since Poac can build itself.
                以上为Poac项目的ReadMe。
                这个项目涉及build system领域，得到了100个star，请问这个项目在build system方面的技术价值如何？
                答：填补了这个领域的空白
                =====以上为问答示例=====
                你应当遵循问答示例的格式进行问答。
                回答的内容应该是一个短句，指出它的重要程度。不准输出其他内容。
                
                $readme
                以上为${repoInfo.name}项目的ReadMe。
                这个项目，使用了${topic}技术，得到了${repoInfo.starsCount}个star。
                请问这个项目在${topic}方面的技术价值如何？
            """.trimIndent()
            )
        }
    }.awaitAll().toMap()
    languageValue + topicValue
}

suspend fun Analyser.analyseNation(userInfo: UserInfo): Estimated<String> = coroutineScope {
    val info = buildString {
        append("用户名为${userInfo.name}")
        userInfo.bio?.let { append("，简介为$it") }
        userInfo.location?.let { append("，位置为$it") }
    }
    inferNationFrom(info)//TODO: infer from connections
}

suspend fun Analyser.inferNationFrom(info: String): Estimated<String> = coroutineScope {
    val respondent = chatApiProvider.asRespondent()
    val nationInferred =
        respondent.chat(
            """
            =====以下为问答示例=====
            问：有这样一位用户，用户名为Tom，位置为New York，请问他最可能在哪个国家？
            答：美国
            =====以上为问答示例=====
            你应当遵循问答示例的格式进行问答。回答的内容应当是一个国家名。不准输出其他内容。
            有这样一位用户，$info，请问他最可能在哪个国家？
        """.trimIndent()
        )
    val belief =
        withRetry(3) {
            respondent.chat(
                """
                    =====以下为问答示例=====
                    问：从信息“用户名为Tom，位置为New York”推断所在国家为美国的置信度是多少？
                    答：100
                    问：从信息“用户名为张三”推断所在国家为中国的置信度是多少？
                    答：30
                    =====以上为问答示例=====
                    你应当遵循问答示例的格式进行问答。
                    回答的内容应当是一个0到100之间的整数，表示你对这个推断的置信度。不准输出其他内容。
                    从信息“${info}”推断所在国家为${nationInferred}的置信度是多少？
                """.trimIndent()
            ).parseInt()
        }
    Estimated(belief, nationInferred)
}

fun String.parseInt(): Int =
    Scanner(this).use {
        it.nextInt()
    }


fun Double.formatted(): String = String.format("%.2f", this)

suspend fun <T> withRetry(count: Int, block: suspend () -> T): T {
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
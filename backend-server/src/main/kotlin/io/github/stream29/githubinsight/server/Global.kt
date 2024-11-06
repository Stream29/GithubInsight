package io.github.stream29.githubinsight.server

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.BackendConfig
import io.github.stream29.githubinsight.analysis.Analyser
import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.entities.UserResult
import io.github.stream29.githubinsight.fromYamlString
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.langchain4kt.utils.SwitchOnFailApiProvider
import java.io.File

val configFile = File("config.yml")
val backendConfig = BackendConfig.fromYamlString(configFile.readText())
val chatApiProvider = backendConfig.chatApi.map { it.getApiProvider() }.let {
    SwitchOnFailApiProvider(it)
}
val githubApiProvider = GithubApiProvider(backendConfig.githubApi[0].token)
val mongoClient = backendConfig.mongodb.connectionString.let { MongoClient.create(it) }
val mongoDatabase = mongoClient.getDatabase("github-insight")
val userInfoCollection = mongoDatabase.getCollection<UserInfo>("user-info")
val userResultCollection = mongoDatabase.getCollection<UserResult>("user-result")
val analyser = Analyser(userInfoCollection, userResultCollection, chatApiProvider, githubApiProvider)
package io.github.stream29.githubinsight.server

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.BackendConfig
import io.github.stream29.githubinsight.analysis.Analyser
import io.github.stream29.githubinsight.fromYamlString
import io.github.stream29.githubinsight.toSwitchOnFailApiProvider
import io.github.stream29.githubinsight.toSpider
import java.io.File

val configFile = File("config.yml")
val backendConfig = BackendConfig.fromYamlString(configFile.readText())
val chatApiProvider = backendConfig.chatApi.toSwitchOnFailApiProvider()
val githubApiProvider = backendConfig.githubApi.toSpider()
val mongoClient = backendConfig.mongodb.connectionString.let { MongoClient.create(it) }
val mongoDatabase = mongoClient.getDatabase("github_insight")
val analyser = Analyser(mongoDatabase, chatApiProvider, githubApiProvider)
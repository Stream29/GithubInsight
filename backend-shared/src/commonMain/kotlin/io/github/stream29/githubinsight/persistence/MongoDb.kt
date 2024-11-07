package io.github.stream29.githubinsight.persistence

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.PersistenceConfig

val client = MongoClient.create()
val database = client.getDatabase("github-insight")

fun PersistenceConfig.getClient() = MongoClient.create(connectionString)
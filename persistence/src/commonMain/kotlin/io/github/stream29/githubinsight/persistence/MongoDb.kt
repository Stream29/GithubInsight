package io.github.stream29.githubinsight.persistence

import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.serialization.Serializable

val client = MongoClient.create()
val database = client.getDatabase("github-insight")

fun PersistenceConfig.getClient() = MongoClient.create(connectionString)


@Serializable
data class User(
    val name: String,
)

suspend fun main() {
    val userCollection = database.getCollection<User>("users")
    userCollection.insertOne(User("Stream"))
}
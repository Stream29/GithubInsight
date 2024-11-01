package io.github.stream29.githubinsight

import kotlinx.serialization.DeserializationStrategy

expect class GithubApiProvider(
    authToken: String
) {
    suspend fun <T> fetch(url: String, serializer: DeserializationStrategy<T>): T
    suspend fun fetchUser(username: String): User
}

const val baseUserUrl = "https://api.github.com/users"
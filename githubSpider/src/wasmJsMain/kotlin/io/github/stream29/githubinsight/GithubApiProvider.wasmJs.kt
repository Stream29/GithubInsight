package io.github.stream29.githubinsight

import kotlinx.serialization.DeserializationStrategy

actual class GithubApiProvider actual constructor(
    authToken: String
) {
    actual suspend fun fetchUser(username: String): UserResponse {
        TODO("Not yet implemented")
    }

    actual suspend fun <T> fetch(url: String, serializer: DeserializationStrategy<T>): T {
        TODO("Not yet implemented")
    }
}
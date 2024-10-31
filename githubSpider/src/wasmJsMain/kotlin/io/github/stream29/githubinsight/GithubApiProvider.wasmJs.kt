package io.github.stream29.githubinsight

actual class GithubApiProvider actual constructor(
    authToken: String
) {
    actual suspend fun fetchUser(username: String): User {
        TODO("Not yet implemented")
    }
}
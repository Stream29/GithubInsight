package io.github.stream29.githubinsight

expect class GithubApiProvider(
    authToken: String
) {
    suspend fun fetchUser(username: String): User
}
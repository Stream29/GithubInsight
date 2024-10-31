package io.github.stream29.githubinsight

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val id: Int,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val url: String,
    val type: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val bio: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("disk_usage")
    val diskUsage: Int,
    val collaborators: Int
)
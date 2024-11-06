package io.github.stream29.githubinsight

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val followersList: List<String>,
    val followingList: List<String>,
    val subscriptions: List<String>,
    val organizations: List<String>,
    val repos: List<String>,
    val siteAdmin: Boolean,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    val bio: String? = null,
    val twitterUsername: String? = null,
    val publicRepos: Long,
    val publicGists: Long,
    val createdAt: String,
    val updatedAt: String,
    val diskUsage: Long? = null,
    val collaborators: Long? = null
)

@Serializable
data class Repository(
    val id: Long,
    val name: String,
    val fullName: String,
    val owner: User? = null,
    val private: Boolean,
    val description: String? = null,
    val fork: Boolean,
    val forks: List<String>,
    val forksCount: Long,
    val contributors: List<String>,
    val openIssuesCount: Long,
    val languages: List<String>,
    val stargazers: List<String>,
    val stargazersCount: Long,
    val watchersCount: Long,
    val size: Long,
    val topics: List<String>,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasWiki: Boolean,
    val hasPages: Boolean,
    val hasDownloads: Boolean,
    val hasDiscussions: Boolean,
    val archived: Boolean,
    val disabled: Boolean,
    val visibility: Boolean,
    val pushedAt: String,
    val createdAt: String,
    val updatedAt: String,
    val permissions: Permissions,
    val allowForking: Boolean,
    val subscribersCount: Long,
    val networkCount: Long,
    val license: License,
)

@Serializable
data class Event(
    val id: Long,
    val type: String,
    val actor: String,
    val repo: String,
)

@Serializable
data class Release(
    val id: Long,
    val author: String,
    val name: String,
    val prerelease: Boolean,
    val createdAt: String,
    val publishedAt: String,
    val assets: Assets,
    val body: String
)

@Serializable
data class Assets(
    val id: Long,
    val uploader: String,
    val state: String,
    val size: Long,
    val downloadCount: Long,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class Commit(
    val sha: String,
    val message: String,
    val author: String,
    val committer: String,
)

@Serializable
data class Issue(
    val id: Long,
    val title: String,
    val user: String,
)

@Serializable
data class IssueEvent(
    val id: Long,
    val actor: String,
    val event: String,
)
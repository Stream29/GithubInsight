package io.github.stream29.githubinsight

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val login: String,
    val id: Int,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("gravatar_id")
    val gravatarId: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("followers_url")
    val followersUrl: String,
    @SerialName("following_url")
    val followingUrl: String,
    @SerialName("starred_url")
    val starredUrl: String,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerialName("repos_url")
    val reposUrl: String,
    val type: String,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    val bio: String? = null,
    @SerialName("twitter_username")
    val twitterUsername: String? = null,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("disk_usage")
    val diskUsage: Int? = null,
    val collaborators: Int? = null
)

@Serializable
data class RepositoryResponse(
    val id: Int,
    @SerialName("node_id")
    val nodeId: String,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: UserResponse? = null,
    val private: Boolean,
    @SerialName("html_url")
    val htmlUrl: String,
    val description: String? = null,
    val fork: Boolean,
    val url: String,
    @SerialName("commits_url")
    val commitsUrl: String,
    @SerialName("contributors_url")
    val contributorsUrl: String,
    @SerialName("forks_url")
    val forksUrl: String,
    @SerialName("issue_comment_url")
    val issueCommentUrl: String,
    @SerialName("issue_events_url")
    val issueEventsUrl: String,
    @SerialName("issues_url")
    val issuesUrl: String,
    @SerialName("languages_url")
    val languagesUrl: String,
    @SerialName("releases_url")
    val releasesUrl: String,
    @SerialName("stargazers_url")
    val stargazersUrl: String,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
    val size: Int,
    @SerialName("open_issues_count")
    val openIssuesCount: Int,
    val topics: List<String>,
    @SerialName("has_issues")
    val hasIssues: Boolean,
    @SerialName("has_projects")
    val hasProjects: Boolean,
    @SerialName("has_wiki")
    val hasWiki: Boolean,
    @SerialName("has_pages")
    val hasPages: Boolean,
    @SerialName("has_downloads")
    val hasDownloads: Boolean,
    @SerialName("has_discussions")
    val hasDiscussions: Boolean,
    val archived: Boolean,
    val disabled: Boolean,
    val visibility: Boolean,
    @SerialName("pushed_at")
    val pushedAt: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val permissions: Permissions,
    @SerialName("allow_forking")
    val allowForking: Boolean,
    @SerialName("subscribers_count")
    val subscribersCount: Int,
    @SerialName("network_count")
    val networkCount: Int,
    val license: License,
)

@Serializable
data class Permissions(
    val pull: Boolean,
    val push: Boolean,
    val admin: Boolean,
)

@Serializable
data class License(
    val key: String,
    val name: String,
    @SerialName("spdx_id")
    val spdxId: String,
    val url: String,
    @SerialName("node_id")
    val nodeId: String,
)

@Serializable
data class ReleaseResponse(
    val nodeId: String,
    val author: UserResponse,
    val name: String,
    val prerelease: Boolean,
    val createdAt: String,
    val publishedAt: String,
    val assets: AssetsResponse,
    val body: String
)

@Serializable
data class AssetsResponse(
    val nodeId: String,
    val uploader: UserResponse,
    val state: String,
    val size: Int,
    val downloadCount: Int,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class CommitResponse(
    val sha: String,
    val message: String,
    val author: UserResponse,
    val committer: UserResponse,
)

@Serializable
data class IssueResponse(
    val nodeId: String,
    val title: String,
    val user: UserResponse,
)

@Serializable
data class IssueEventResponse(
    val nodeId: String,
    val actor: UserResponse,
    val event: String,
)


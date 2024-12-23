package io.github.stream29.githubinsight.spider

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCollection(
    val userResponse: UserResponse,
    val orgsResponse: List<OrganizationResponse>,
    val reposResponse: List<RepositoryResponse>,
    val subscriptionsResponse: List<RepositoryResponse>,
    val followersResponse: List<UserResponse>,
    val followingResponse: List<UserResponse>,
)

@Serializable
data class UserResponse(
    val login: String,
    val id: Long,
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
    @SerialName("organizations_url")
    val organizationsUrl: String,
    @SerialName("repos_url")
    val reposUrl: String,
    @SerialName("events_url")
    val eventsUrl: String,
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
    val publicRepos: Long? = null,
    @SerialName("public_gists")
    val publicGists: Long? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("disk_usage")
    val diskUsage: Long? = null,
    val collaborators: Long? = null
)

@Serializable
data class RepositoryResponse(
    val id: Long,
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
    @SerialName("tags_url")
    val tagsUrl: String,
    @SerialName("collaborators_url")
    val collaboratorsUrl: String,
    @SerialName("contributors_url")
    val contributorsUrl: String,
    @SerialName("subscribers_url")
    val subscribersUrl: String,
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
    val forksCount: Long,
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    @SerialName("watchers_count")
    val watchersCount: Long,
    val size: Long,
    @SerialName("open_issues_count")
    val openIssuesCount: Long,
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
    val visibility: String,
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
    val subscribersCount: Long? = null,
    @SerialName("network_count")
    val networkCount: Long? = null,
    val license: License? = null,
    val readme: String? = null,
)

@Serializable
data class TagResponse(
    val name: String,
    @SerialName("zipball_url")
    val zipballUrl: String,
    @SerialName("tarball_url")
    val tarballUrl: String,
    val commit: CommitResponse,
    @SerialName("node_id")
    val nodeId: String,
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
    val url: String? = null,
    @SerialName("node_id")
    val nodeId: String,
)

@Serializable
data class Readme(
    val name: String,
    val path: String,
    val sha: String,
    val size: Long,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val content: String,
    val encoding: String,
)

@Serializable
data class ReleaseResponse(
    val id: Long,
    val author: UserResponse,
    val name: String,
    val prerelease: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("published_at")
    val publishedAt: String,
    val assets: List<AssetResponse>,
    val body: String
)

@Serializable
data class AssetResponse(
    val id: Long,
    val uploader: UserResponse,
    val state: String,
    val size: Long,
    @SerialName("download_count")
    val downloadCount: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)

@Serializable
data class CommitResponse(
    val sha: String,
    val url: String? = null,
    val message: String? = null,
    val author: UserResponse? = null,
    val committer: UserResponse? = null,
)

@Serializable
data class IssueResponse(
    val id: Long,
    val title: String,
    val user: UserResponse,
)

@Serializable
data class IssueEventResponse(
    val id: Long,
    val actor: UserResponse,
    val event: String,
)

@Serializable
data class OrganizationResponse(
    val id: Long,
    val login: String,
    @SerialName("members_url")
    val membersUrl: String,
    val description: String? = null,
)

@Serializable
data class EventResponse(
    val id: Long,
    val type: String,
    val actor: UserResponse,
    val repo: RepositoryResponse,
    val payload: Payload,
    val public: Boolean,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class Payload(
    @SerialName("repository_id")
    val repositoryId: String,
    @SerialName("push_id")
    val pushId: String,
    val size: Long,
    val ref: String,
    val head: String,
    val before: String,
    val commits: List<CommitResponse>? = null,
)
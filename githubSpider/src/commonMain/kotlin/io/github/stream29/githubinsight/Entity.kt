package io.github.stream29.githubinsight

data class User (
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val followersList: List<User>,
    val followingList: List<User>,
    val subscriptions: List<Repository>,
    val repos: List<Repository>,
    val siteAdmin: Boolean,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    val bio: String? = null,
    val twitterUsername: String? = null,
    val publicRepos: Int,
    val publicGists: Int,
    val createdAt: String,
    val updatedAt: String,
    val diskUsage: Int? = null,
    val collaborators: Int? = null
)

data class Repository(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: User? = null,
    val private: Boolean,
    val description: String? = null,
    val fork: Boolean,
    val forks: List<Repository>,
    val forksCount: Int,
    val contributors: List<User>,
    val commits: List<Commit>,
    val issues: List<Issue>,
    val issueEvents: List<IssueEvent>,
    val openIssuesCount: Int,
    val languages: List<String>,
    val releases: List<Release>,
    val stargazers: List<User>,
    val stargazersCount: Int,
    val watchersCount: Int,
    val size: Int,
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
    val subscribersCount: Int,
    val networkCount: Int,
    val license: License,
)

data class Release(
    val id: Int,
    val author: User,
    val name: String,
    val prerelease: Boolean,
    val createdAt: String,
    val publishedAt: String,
    val assets: Assets,
    val body: String
)

data class Assets(
    val id: Int,
    val uploader: User,
    val state: String,
    val size: Int,
    val downloadCount: Int,
    val createdAt: String,
    val updatedAt: String,
)

data class Commit(
    val sha: String,
    val message: String,
    val author: User,
    val committer: User,
)

data class Issue(
    val nodeId: String,
    val title: String,
    val user: User,
)

data class IssueEvent(
    val nodeId: String,
    val actor: User,
    val event: String,
)
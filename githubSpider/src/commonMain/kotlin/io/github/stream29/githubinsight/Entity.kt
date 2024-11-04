package io.github.stream29.githubinsight

data class User(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val followersList: List<User>,
    val followingList: List<User>,
    val subscriptions: List<Repository>,
    val organizations: List<Organization>,
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
    val publicRepos: Long,
    val publicGists: Long,
    val createdAt: String,
    val updatedAt: String,
    val diskUsage: Long? = null,
    val collaborators: Long? = null
)

data class Repository(
    val id: Long,
    val name: String,
    val fullName: String,
    val owner: User? = null,
    val private: Boolean,
    val description: String? = null,
    val fork: Boolean,
    val forks: List<Repository>,
    val forksCount: Long,
    val contributors: List<User>,
    val commits: List<Commit>,
    val issues: List<Issue>,
    val issueEvents: List<IssueEvent>,
    val openIssuesCount: Long,
    val languages: List<String>,
    val releases: List<Release>,
    val stargazers: List<User>,
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

data class Release(
    val nodeId: String,
    val author: User,
    val name: String,
    val prerelease: Boolean,
    val createdAt: String,
    val publishedAt: String,
    val assets: Assets,
    val body: String
)

data class Assets(
    val nodeId: String,
    val uploader: User,
    val state: String,
    val size: Long,
    val downloadCount: Long,
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

data class Organization(
    val login: String,
    val nodeId: String,
    val avatarUrl: String,
    val description: String,
    val email: String,
    val isVerified: Boolean,
    val publicRepos: Long,
    val followers: Long,
)
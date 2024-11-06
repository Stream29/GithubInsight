package io.github.stream29.githubinsight.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserResult(
    val login: String,
    val talentRank: ContributionVector<String>,
    val nation: Estimated<String>
)

@Serializable
data class UserInfo(
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bio: String?,
    val email: String?,
    val organizations: List<String>?,
    val followers: List<String>,
    val following: List<String>,
//    订阅的仓库
    val subscriptions: List<String>,
//    存储仓库请求路径：{login}/{repoName}
    val repos: List<String>,
    val company: String?,
    val blog: String?,
    val location: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followersAmount: Int,
    val followingAmount: Int
)

@Serializable
data class Repository(
    val name: String,
    val htmlUrl: String,
    val description: String?,
    val collaborators: List<String>,
    val teams: List<String>,
    val tags: List<String>,
//    使用的编程语言以及使用次数
    val languages: Map<String,Int>,
    val stargazers: List<String>,
    val contributors: List<String>,
    val subscribers: List<String>,
    val commits: UserCommit,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val topics: List<String>
)

@Serializable
data class Organization(
    val login: String,
    val members: List<String>,
    val description: String?
)

@Serializable
data class UserCommit(
    val author: String,
    val message: String,
)

@Serializable
data class Estimated<T>(
    val belief: Int,
    val value: T
)

@Serializable
data class ContributionVector<T>(
    val contributionMap: Map<String, T>
)
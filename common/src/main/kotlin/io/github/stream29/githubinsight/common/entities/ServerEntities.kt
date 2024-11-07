package io.github.stream29.githubinsight.common.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserResult(
    val login: String,
    val talentRank: ContributionVector,
    val nation: Estimated<String>
)

@Serializable
data class UserInfo(
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bio: String? = null,
    val email: String? = null,
    val organizations: List<String>? = null,
    val followers: List<String>,
    val following: List<String>,
//    订阅的仓库
    val subscriptions: List<String>,
//    存储仓库请求路径：{login}/{repoName}
    val repos: List<String>,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
)

@Serializable
data class RepoResult(
    val name: String,
    val techValue: Map<String, String>,
    val contributeMap: Map<String, Int>,
    val contributionTotal: Int
)

@Serializable
data class Repository(
    val name: String,
    val description: String? = null,
    val collaborators: List<String>,
    val tags: List<String>,
//    使用的编程语言以及使用次数
    val languages: Map<String, Int>,
    val contributors: List<String>,
    val subscribers: List<String>,
    val commits: List<UserCommit>,
    val starsCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val topics: List<String>,
    val readme: String? = null
)

@Serializable
data class Organization(
    val login: String,
    val members: List<String>,
    val description: String? = null
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
data class ContributionVector(
    val contributionMap: Map<String, Pair<String,Int>>
)
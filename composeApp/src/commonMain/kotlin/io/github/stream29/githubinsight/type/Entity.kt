package io.github.stream29.githubinsight.type

data class Entity(
    val userInfo: UserInfo,
    val userResult: UserResult,
    val talentRank: ContributionVector<Int>
)

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
    val publicRepos: Int,
    val publicGists: Int,
    val followersAmount: Int,
    val followingAmount: Int
)

data class UserResult(
    val login: String,
    val talentRank: ContributionVector<String>,
    val nation: Estimated<String>
)

data class ContributionVector<T>(
    val contributionMap: Map<String, T>
)

data class Estimated<T>(
    val belief: Int,
    val value: T
)
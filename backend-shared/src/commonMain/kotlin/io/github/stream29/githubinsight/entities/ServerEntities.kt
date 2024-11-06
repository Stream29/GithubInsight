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
    val followingUrl: List<String>,
//    订阅的仓库或用户
    val subscriptionsUrl: String,
    val reposUrl: String,
//    val events: List<UserEvent>?
    val company: String?,
    val blog: String?,
    val location: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followersAmount: Int,
    val followingAmount: Int
)

//暂时不考虑，类型太多
//data class UserEvent(
//    val type: String,
//    val repo: String,
//    val payload: Payload
//)

@Serializable
data class Estimated<T>(
    val belief: Int,
    val value: T
)

@Serializable
data class ContributionVector<T>(
    val contributionMap: Map<String, T>
)
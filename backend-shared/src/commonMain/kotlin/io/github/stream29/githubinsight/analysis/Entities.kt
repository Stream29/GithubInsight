package io.github.stream29.githubinsight.analysis

import io.github.stream29.githubinsight.spider.Organization
import io.github.stream29.githubinsight.spider.Repository


data class UserResult(
    val info: UserInfo,
    val talentRank: UserTalentRank,
    val nation: UserNation,
)

data class UserAnalysisData(
    val info: UserInfo,
    val followers: List<UserInfo>,
    val followings: List<UserInfo>,
    val repository: Repository,
)

data class UserInfo(
    val name: String,
    val avatar: String,
    val profile: String,
    val email: String?,
    val organization: Organization
)

data class UserTalentRank(
    val contributionVector: ContributionVector,
    val confidence: Double?
)

data class UserNation(
    val isEstimate: Boolean,
    val nation: String,
    val confidence: Double?
)
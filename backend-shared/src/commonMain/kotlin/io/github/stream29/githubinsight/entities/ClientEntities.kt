package io.github.stream29.githubinsight.entities

data class ClientEntities(
    val userInfo: UserInfo,
    val userResult: UserResult,
    val talentRank: ContributionVector<Int>
)

//need process into clientMap = Map<login,ClientEntitles>
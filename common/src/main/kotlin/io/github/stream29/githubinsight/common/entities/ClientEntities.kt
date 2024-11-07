package io.github.stream29.githubinsight.common.entities

import io.github.stream29.githubinsight.common.entities.ContributionVector
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.common.entities.UserResult

data class ClientEntities(
    val userInfo: UserInfo,
    val userResult: UserResult,
)

//need process into clientMap = Map<login,ClientEntitles>
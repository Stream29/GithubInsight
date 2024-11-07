package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.common.entities.ClientEntities
import io.github.stream29.githubinsight.common.entities.ContributionVector
import io.github.stream29.githubinsight.common.entities.Estimated
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.common.entities.UserResult
import kotlinx.coroutines.runBlocking

var user: ClientEntities? = ClientEntities(
    userInfo = UserInfo(
        login = "Stream29",
        name = "Stream29",
        avatarUrl = "https://avatars.githubusercontent.com/u/21029310?v=4",
        bio = "I'm a student",
        email = "",
        followers = listOf(),
        following = listOf(),
        subscriptions = listOf(),
        repos = listOf(),
    ),
    userResult = UserResult(
        login = "Stream29",
        talentRank = ContributionVector(
            mapOf(
                "Kotlin" to ("Good" to 100),
                "Java" to ("Excellent" to 95),
                "Python" to ("Intermediate" to 75),
                "JavaScript" to ("Beginner" to 50),
                "C++" to ("Advanced" to 85),
                "Swift" to ("Novice" to 40)
            )
        ),
        nation = Estimated(
            belief = 80,
            value = "China"
        )
    ),
)
var userList: List<ClientEntities>? = null

fun UserEntities(userLogin: String) = runBlocking { backendApiProvider.getUser(userLogin) }

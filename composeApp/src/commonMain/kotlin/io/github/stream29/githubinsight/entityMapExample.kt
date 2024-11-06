package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.type.ContributionVector
import io.github.stream29.githubinsight.type.Entity
import io.github.stream29.githubinsight.type.Estimated
import io.github.stream29.githubinsight.type.UserInfo
import io.github.stream29.githubinsight.type.UserResult

val entityMapExample = mapOf("stream" to Entity(
    userInfo = UserInfo(
        login = "stream",
        name = "stream",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        bio = "I am stream, a developer",
        email = "email",
        organizations = listOf("org1", "org2"),
        followers = listOf("follower1", "follower2"),
        following = listOf("following1", "following2"),
        subscriptions = listOf("sub1", "sub2"),
        repos = listOf("repo1", "repo2"),
        company = "company",
        blog = "blog",
        publicRepos = 1,
        publicGists = 1,
        followersAmount = 1,
        followingAmount = 1
    ),
    userResult = UserResult(
        login = "stream",
        talentRank = ContributionVector(mapOf("key" to "value")),
        nation = Estimated(100, "China")
    ),
    talentRank = ContributionVector(mapOf("key" to 1))
))
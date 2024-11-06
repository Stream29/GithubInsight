package io.github.stream29.githubinsight.spider

import kotlinx.serialization.Serializable

@Serializable
data class JsonCollection(
    val jsonUrl: String,
    val json: String
)
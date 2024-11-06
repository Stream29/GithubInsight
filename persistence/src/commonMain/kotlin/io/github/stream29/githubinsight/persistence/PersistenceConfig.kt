package io.github.stream29.githubinsight.persistence

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersistenceConfig(
    @SerialName("connection_string")
    val connectionString: String
)


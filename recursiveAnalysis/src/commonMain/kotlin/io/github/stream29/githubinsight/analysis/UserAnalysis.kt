package io.github.stream29.githubinsight.analysis

import io.ktor.client.*

data class AnalyzeConfig(
    val httpClient: HttpClient,
)
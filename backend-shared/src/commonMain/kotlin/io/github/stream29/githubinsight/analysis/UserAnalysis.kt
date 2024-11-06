package io.github.stream29.githubinsight.analysis

import kotlin.jvm.JvmInline

@JvmInline
value class ContributionVector<T>(
    val contributionMap: Map<String, T>
)

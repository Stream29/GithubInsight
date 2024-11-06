package io.github.stream29.githubinsight.analysis

import kotlin.jvm.JvmInline

@JvmInline
value class ContributionVector(
    val contributionMap: Map<String, Int>
)

operator fun ContributionVector.plus(other: ContributionVector): ContributionVector {
    val newMap = mutableMapOf<String, Int>()
    newMap.putAll(contributionMap)
    other.contributionMap.forEach { (k, v) ->
        newMap[k] = newMap.getOrElse(k) { 0 } + v
    }
    return ContributionVector(newMap)
}
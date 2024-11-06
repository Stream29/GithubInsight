import io.github.stream29.githubinsight.analysis.ContributionVector
import io.github.stream29.githubinsight.analysis.plus
import kotlin.test.Test

class ContributionVectorTest {
    @Test
    fun test() {
        val contributionVector1 = ContributionVector(mapOf("a" to 1, "b" to 2))
        val contributionVector2 = ContributionVector(mapOf("b" to 3, "c" to 4))
        val result = contributionVector1 + contributionVector2
        assert(result.contributionMap == mapOf("a" to 1, "b" to 5, "c" to 4))
    }
}
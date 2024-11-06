import io.github.stream29.githubinsight.spider.BalancingApiProvider
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.githubinsight.spider.fetchBase
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BalancingTest {
    @Test
    fun balancingApiTest() {
        val apiProviders = listOf(
            GithubApiProvider(
                authToken = System.getenv("GITHUB_TOKEN")
            )
        )
        val balancingApiProvider = BalancingApiProvider(apiProviders)

        runBlocking {
            val result = balancingApiProvider.execute { apiProvider ->
                runBlocking {
                    apiProvider.fetchBase(System.getenv("GITHUB_ACTOR"))
                }
            }
            println(result)
        }
    }
}
import io.github.stream29.githubinsight.spider.BalancingApiProvider
import io.github.stream29.githubinsight.spider.GithubApiProvider
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BalancingTest {
    @Test
    fun balancingApiTest() {
        val apiProviders = listOf(
            GithubApiProvider(
                httpClient = httpClient,
                authToken = System.getenv("GITHUB_PUBLIC_TOKEN")
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
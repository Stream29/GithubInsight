import io.github.stream29.githubinsight.BalancingApiProvider
import io.github.stream29.githubinsight.GithubApiProvider
import io.github.stream29.githubinsight.httpClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BalancingTest {
    @Test
    fun balancingApiTest() {
        val apiProviders = listOf(GithubApiProvider(
            httpClient = httpClient,
            authToken = System.getenv("GITHUB_PUBLIC_TOKEN")
        ))
        val balancingApiProvider = BalancingApiProvider(apiProviders)

        runBlocking {
            val result = balancingApiProvider.execute{ apiProvider ->
                runBlocking {
                    apiProvider.fetchBase(System.getenv("GITHUB_ACTOR"))
                }
            }
            println(result)
        }
    }
}
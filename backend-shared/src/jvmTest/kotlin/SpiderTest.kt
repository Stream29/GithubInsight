import io.github.stream29.githubinsight.*
import io.github.stream29.githubinsight.spider.BalancingApiProvider
import io.github.stream29.githubinsight.spider.GithubApiProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test

class SpiderTest {
    @Test
    fun spiderTest() {
        val balancingApiProvider = BalancingApiProvider(
            listOf(
                GithubApiProvider(
                    httpClient = httpClient,
                    authToken = System.getenv("GITHUB_TOKEN")
                )
            )
        )
        val spider = Spider(balancingApiProvider)

        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        val login = System.getenv("GITHUB_ACTOR")

        runBlocking {
            println(json.encodeToString(User.serializer(), spider.getBaseUserInformation(login)))
        }
    }
}
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.githubinsight.spider.ResponseCollection
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class ApiTest {
    @Test
    fun fetchAllTest() {
        val githubApiProvider = GithubApiProvider(
            httpClient = httpClient,
            authToken = System.getenv("GITHUB_PUBLIC_TOKEN")
        )
        val responseCollection = runBlocking {
            githubApiProvider.fetchBase(System.getenv("GITHUB_ACTOR"))
        }
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        println(json.encodeToString(ResponseCollection.serializer(), responseCollection))
    }
}
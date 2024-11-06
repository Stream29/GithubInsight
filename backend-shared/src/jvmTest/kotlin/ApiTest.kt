import io.github.stream29.githubinsight.GithubApiProvider
import io.github.stream29.githubinsight.ResponseCollection
import io.github.stream29.githubinsight.httpClient
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
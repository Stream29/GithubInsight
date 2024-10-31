import io.github.stream29.githubinsight.GithubApiProvider
import io.github.stream29.githubinsight.User
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class ApiTest {
    @Test
    fun userUrlTest() {
        val githubApiProvider = GithubApiProvider(
            authToken = System.getenv("GITHUB_TOKEN")
        )
        val response = runBlocking {
            githubApiProvider.fetchUser(System.getenv("GITHUB_ACTOR"))
        }
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(User.serializer(), response)
        println(jsonString)
    }
}
import io.github.stream29.githubinsight.EntityProcessor
import io.github.stream29.githubinsight.GithubApiProvider
import io.github.stream29.githubinsight.User
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test

class EntityTest {
    @Test
    fun processTest() {
        val githubApiProvider = GithubApiProvider(
            authToken = System.getenv("GITHUB_PUBLIC_TOKEN")
        )
        val responseCollection = runBlocking {
            githubApiProvider.fetchAll(System.getenv("GITHUB_ACTOR"))
        }
        val user = EntityProcessor().process(responseCollection)
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        println(json.encodeToString(User.serializer(), user))
    }
}
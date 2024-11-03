import io.github.stream29.githubinsight.GithubApiProvider
import io.github.stream29.githubinsight.User
import io.github.stream29.githubinsight.UserResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test

class ApiTest {
    @Test
    fun userUrlTest() {
        val githubApiProvider = GithubApiProvider(
            authToken = System.getenv("GITHUB_TOKEN")
        )
        runBlocking {
//            githubApiProvider.fetchAll(System.getenv("GITHUB_ACTOR"))
            githubApiProvider.fetchAll("sakura-ryoko")
        }
    }
}
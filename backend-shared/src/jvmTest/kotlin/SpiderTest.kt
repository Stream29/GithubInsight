import io.github.stream29.githubinsight.spider.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test

class SpiderTest {
    @Test
    fun spiderTest() {
        val balancingApiProvider = BalancingApiProvider(
            listOf(
                GithubApiProvider(
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

//        runBlocking {
//            println(json.encodeToString(User.serializer(), spider.getUserInfo(login)))
//        }

        runBlocking {
            println(json.encodeToString(Repository.serializer(), spider.getRepository("Moistrocic/langchain4kt")))
        }
    }
}
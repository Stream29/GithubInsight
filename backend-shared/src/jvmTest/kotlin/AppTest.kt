import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.BackendConfig
import io.github.stream29.githubinsight.analysis.Analyser
import io.github.stream29.githubinsight.analysis.analyseUser
import io.github.stream29.githubinsight.entities.UserInfo
import io.github.stream29.githubinsight.entities.UserResult
import io.github.stream29.githubinsight.fromYamlString
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.langchain4kt.utils.SwitchOnFailApiProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.Test

class AppTest {
    @Test
    fun test() {
        val backendConfig = File("src/jvmTest/resources/config.yml")
            .readText()
            .let { BackendConfig.fromYamlString(it) }
        val chatApiProvider = backendConfig.chatApi.map { it.getApiProvider(httpClient) }.let {
            SwitchOnFailApiProvider(it)
        }
        val githubApiProvider = GithubApiProvider(httpClient, backendConfig.githubApi[0].token)
        val mongoClient = backendConfig.mongodb.connectionString.let { MongoClient.create(it) }
        val mongoDatabase = mongoClient.getDatabase("github-insight")
        val userInfoCollection = mongoDatabase.getCollection<UserInfo>("user-info")
        val userResultCollection = mongoDatabase.getCollection<UserResult>("user-result")
        val analyser = Analyser(userInfoCollection, userResultCollection, chatApiProvider, githubApiProvider)
        runBlocking {
            println(
                Json.encodeToString(
                    listOf(
                        analyser.analyseUser("Stream29"),
                        analyser.analyseUser("yumeowo"),
                        analyser.analyseUser("Moistrocic")
                    )
                )
            )
        }
    }
}
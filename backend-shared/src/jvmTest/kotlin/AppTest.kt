import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.BackendConfig
import io.github.stream29.githubinsight.analysis.Analyser
import io.github.stream29.githubinsight.analysis.analyseUser
import io.github.stream29.githubinsight.fromYamlString
import io.github.stream29.githubinsight.toSpider
import io.github.stream29.githubinsight.toSwitchOnFailApiProvider
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test

class AppTest {
    @Test
    fun test() {
        val backendConfig = File("src/jvmTest/resources/config.yml")
            .readText()
            .let { BackendConfig.fromYamlString(it) }
        val chatApiProvider = backendConfig.chatApi.toSwitchOnFailApiProvider()
        val mongoClient = backendConfig.mongodb.connectionString.let { MongoClient.create(it) }
        val mongoDatabase = mongoClient.getDatabase("github_insight")
        val githubApiProvider = backendConfig.toSpider(mongoDatabase)
        val analyser = Analyser(mongoDatabase, chatApiProvider, githubApiProvider)
        runBlocking {
            println(analyser.analyseUser("ConstasJ"))
            println(analyser.analyseUser("Stream29"))
        }
    }
}
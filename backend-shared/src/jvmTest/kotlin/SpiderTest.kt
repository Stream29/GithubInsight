import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.stream29.githubinsight.BackendConfig
import io.github.stream29.githubinsight.common.entities.Organization
import io.github.stream29.githubinsight.common.entities.Repository
import io.github.stream29.githubinsight.common.entities.UserInfo
import io.github.stream29.githubinsight.fromYamlString
import io.github.stream29.githubinsight.spider.*
import io.github.stream29.githubinsight.spider.utils.BalancingApiProvider
import io.github.stream29.githubinsight.spider.utils.GithubApiProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import java.io.File

class SpiderTest {
    @Test
    fun spiderTest() {
        val backendConfig = File("src/jvmTest/resources/config.yml")
            .readText()
            .let { BackendConfig.fromYamlString(it) }
        val mongoClient = backendConfig.mongodb.connectionString.let { MongoClient.create(it) }
        val mongoDatabase = mongoClient.getDatabase("github_insight")

        val balancingApiProvider = BalancingApiProvider(
            listOf(
                GithubApiProvider(
                    authToken = System.getenv("GITHUB_TOKEN"),
                    mongoDatabase
                )
            )
        )
        val spider = Spider(balancingApiProvider)

        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        runBlocking {
            println(json.encodeToString(UserInfo.serializer(), spider.getUserInfo("Moistrocic")))
            spider.getRepository("ChrisRM/material-theme-jetbrains")
                ?.let { println(json.encodeToString(Repository.serializer(), it)) }
            spider.getOrganization("JetBrains")
                ?.let { println(json.encodeToString(Organization.serializer(), it)) }
        }
    }
}
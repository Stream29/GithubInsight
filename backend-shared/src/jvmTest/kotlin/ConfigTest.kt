import io.github.stream29.githubinsight.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test

class ConfigTest {
    @Test
    fun write() {
        val config = BackendConfig(
            PersistenceConfig("mongodb://localhost:27017"),
            listOf(
                BaiduQianfanAuthConfig(
                    apiKey = System.getenv("BAIDU_QIANFAN_API_KEY")!!,
                    secretKey = System.getenv("BAIDU_QIANFAN_SECRET_KEY")!!,
                    model = "ernie-4.0-8k-latest"
                ),
                GoogleGeminiConfig(
                    apiKey = System.getenv("GOOGLE_AI_GEMINI_API_KEY")!!,
                    model = "gemini-1.5-flash"
                )
            ),
            githubApi = listOf(
                GithubAuthConfig(
                    actor = System.getenv("GITHUB_ACTOR")!!,
                    token = System.getenv("GITHUB_TOKEN")!!
                )
            )
        )
        println(yaml.encodeToString(config))
    }
}
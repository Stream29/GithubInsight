package io.github.stream29.githubinsight

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.stream29.githubinsight.spider.Spider
import io.github.stream29.githubinsight.spider.utils.BalancingApiProvider
import io.github.stream29.githubinsight.spider.utils.GithubApiProvider
import io.github.stream29.langchain4kt.api.baiduqianfan.QianfanApiProvider
import io.github.stream29.langchain4kt.api.googlegemini.GeminiApiProvider
import io.github.stream29.langchain4kt.api.googlegemini.GeminiGenerationConfig
import io.github.stream29.langchain4kt.core.ChatApiProvider
import io.github.stream29.langchain4kt.utils.SwitchOnFailApiProvider
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

fun BackendConfig.Companion.fromYamlString(yamlString: String) =
    yaml.decodeFromString<BackendConfig>(yamlString)

fun BackendConfig.toYamlString() =
    yaml.encodeToString(this)

@Serializable
data class BackendConfig(
    val mongodb: PersistenceConfig,
    val chatApi: List<ChatApiConfig>,
    val githubApi: List<GithubAuthConfig>
) {
    companion object
}

@Serializable
data class GithubAuthConfig(
    val token: String
)

fun GithubAuthConfig.toGithubApiProvider(database: MongoDatabase) =
    GithubApiProvider(token, database)

fun BackendConfig.toSpider(database: MongoDatabase) =
    Spider(BalancingApiProvider(githubApi.map { it.toGithubApiProvider(database) }))

@Serializable
sealed interface ChatApiConfig {
    fun getApiProvider(): ChatApiProvider<*>
}

@Serializable
@SerialName("baidu_qianfan")
data class BaiduQianfanAuthConfig(
    @SerialName("api_key")
    val apiKey: String,
    @SerialName("secret_key")
    val secretKey: String,
    val model: String
) : ChatApiConfig {
    override fun getApiProvider() =
        QianfanApiProvider(httpClient, model, apiKey, secretKey)
}

@Serializable
@SerialName("google_gemini")
data class GoogleGeminiConfig(
    @SerialName("api_key")
    val apiKey: String,
    val model: String
) : ChatApiConfig {
    override fun getApiProvider() =
        GeminiApiProvider(httpClient, GeminiGenerationConfig(), model, apiKey)
}

fun List<ChatApiConfig>.toSwitchOnFailApiProvider() =
    SwitchOnFailApiProvider(this.map { it.getApiProvider() })

@Serializable
data class PersistenceConfig(
    @SerialName("connection_string")
    val connectionString: String
)
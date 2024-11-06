package io.github.stream29.githubinsight

import io.github.stream29.langchain4kt.api.baiduqianfan.QianfanApiProvider
import io.github.stream29.langchain4kt.api.googlegemini.GeminiApiProvider
import io.github.stream29.langchain4kt.api.googlegemini.GeminiGenerationConfig
import io.github.stream29.langchain4kt.core.ChatApiProvider
import io.ktor.client.*
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
    val actor: String,
    val token: String
)

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
        QianfanApiProvider(httpClient, apiKey, secretKey, model)
}

@Serializable
@SerialName("google_gemini")
data class GoogleGeminiConfig(
    @SerialName("api_key")
    val apiKey: String,
    val model: String
) : ChatApiConfig {
    override fun getApiProvider() =
        GeminiApiProvider(httpClient, GeminiGenerationConfig(), apiKey, model)
}

@Serializable
data class PersistenceConfig(
    @SerialName("connection_string")
    val connectionString: String
)
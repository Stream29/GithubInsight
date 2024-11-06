package io.github.stream29.githubinsight.spider

data class BalancingApiProvider(
    val apiProviders: List<GithubApiProvider>,
    var currentApiProviderIndex: Int = 0,
    var currentFailSum: Int = 0,
) {
    suspend fun <R> execute(apiCall: suspend (GithubApiProvider) -> R): R {
        try {
            val apiProvider = apiProviders[currentApiProviderIndex]
            val result = apiCall(apiProvider)
            currentFailSum = 0
            currentApiProviderIndex = (currentApiProviderIndex + 1) % apiProviders.size
            return result
        } catch (e: Exception) {
            currentFailSum++
            currentApiProviderIndex = (currentApiProviderIndex + 1) % apiProviders.size
            if (currentFailSum == apiProviders.size) {
                throw FetchException("All Api Providers failed.", e)
            }
            return execute(apiCall)
        }
    }
}
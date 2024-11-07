package io.github.stream29.githubinsight.common

import io.github.stream29.githubinsight.common.entities.ClientEntities
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface BackendApiProvider {
    suspend fun getUser(login: String): ClientEntities
    suspend fun allUser(begin: Int, end: Int): List<ClientEntities>
}

data class RemoteBackendApiProvider(
    val httpClient: HttpClient,
    val baseUrl: String
) : BackendApiProvider {
    override suspend fun getUser(login: String): ClientEntities {
        return httpClient.get("$baseUrl/user/$login").body()
    }

    override suspend fun allUser(begin: Int, end: Int): List<ClientEntities> {
        TODO("Not yet implemented")
    }

}
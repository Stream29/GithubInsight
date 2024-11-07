package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.common.entities.ClientEntities
import kotlinx.coroutines.runBlocking

var user: ClientEntities? = null
var userList: List<ClientEntities>? = null

fun UserEntities(userLogin: String) = runBlocking { backendApiProvider.getUser(userLogin) }

package io.github.stream29.githubinsight.server

import io.github.stream29.githubinsight.analysis.analyseUser
import io.github.stream29.githubinsight.common.entities.ClientEntities
import io.github.stream29.githubinsight.common.entities.UserResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.toList

fun Application.configureRouting() {
    routing {
        get("user/{login}") {
            val login = call.parameters["login"]
                ?: return@get call.respondText("Missing or malformed login")
            log.info("accept request for user $login, ip ${call.request.headers.toMap()}")
            val result = ClientEntities(
                analyser.githubSpider.getUserInfo(login),
                analyser.analyseUser(login)
            )
            call.respond(result)
        }
        get("users") {
            val begin = call.parameters["begin"]?.toIntOrNull() ?: 0
            val end = call.parameters["end"]?.toIntOrNull() ?: Int.MAX_VALUE
            log.info("accept request for users $begin to $end, ip ${call.request.headers.toMap()}")
            val userList = analyser.userResultCollection.find<UserResult>().skip(begin).limit(end - begin).toList()
            val result = userList.map {
                async {
                    ClientEntities(
                        analyser.githubSpider.getUserInfo(it.login),
                        it
                    )
                }
            }.awaitAll()
            call.respond(result)
        }
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

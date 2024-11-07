package io.github.stream29.githubinsight.server

import io.github.stream29.githubinsight.analysis.analyseUser
import io.github.stream29.githubinsight.common.entities.ClientEntities
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

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
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

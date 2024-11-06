package io.github.stream29.githubinsight.server

import io.github.stream29.githubinsight.analysis.analyseUser
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("user/{login}") {
            val login = call.parameters["login"]
                ?: return@get call.respondText("Missing or malformed login")
            val result = analyser.analyseUser(login)
            call.respond(result)
        }
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

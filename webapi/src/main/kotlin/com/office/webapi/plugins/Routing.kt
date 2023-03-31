package com.office.webapi.plugins

import com.office.webapi.routes.Office
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api") {
            route("v1") {
                Office()
            }

        }

    }
}
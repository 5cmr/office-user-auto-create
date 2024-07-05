package com.office.webapi.plugins

import com.office.webapi.routes.announcementRoute
import com.office.webapi.routes.office
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api") {
            route("v1") {
                office()
                announcementRoute()
            }

        }

    }
}
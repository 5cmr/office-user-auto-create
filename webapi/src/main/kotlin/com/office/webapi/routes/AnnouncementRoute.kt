package com.office.webapi.routes

import com.office.webapi.controllers.AnnouncementController
import com.office.webapi.resultmodels.ResultBase
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.announcementRoute() {
    route("/announcement") {
        val announcementController = AnnouncementController()

        get("/{id}") {
            val id = call.parameters["id"]?.toLong() ?: 0
            val result = ResultBase()
            val dto = announcementController.get(id)
            result.data = dto
            call.respond(result)
        }
    }
}
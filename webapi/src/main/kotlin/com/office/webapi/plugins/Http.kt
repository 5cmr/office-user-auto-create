package com.office.webapi.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import com.office.webapi.resultmodels.ResultBase
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowHeader(HttpHeaders.ContentType)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        allowHost("0.0.0.0:8080")
        //val cors = PropertiesUtil.getValueString("app.cors")!!
        //hosts.addAll(cors.split(',').toMutableSet())
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(StatusPages) {
        exception<Exception> { call, cause ->
            val result = ResultBase()
            result.error = HttpStatusCode.InternalServerError.value
            result.success = false
            result.errorMsg = cause.message ?: ""
            call.respond(HttpStatusCode.InternalServerError, result)
        }
    }


    routing {
//        get("/") {
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }
//
//        get<MyLocation> {
//            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
//        }
//        // Register nested routes
//        get<Type.Edit> {
//            call.respondText("Inside $it")
//        }
//        get<Type.List> {
//            call.respondText("Inside $it")
//        }
//
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        // /swagger-ui/index.html?url=/openapi.json
//        get("/") {
//            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
//        }
    }
}
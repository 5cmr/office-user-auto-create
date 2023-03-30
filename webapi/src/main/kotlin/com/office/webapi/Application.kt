package com.office.webapi

import com.fasterxml.jackson.databind.SerializationFeature
import com.prprpr.core.util.PropertiesUtil
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.get
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import org.jetbrains.exposed.sql.Database
import com.office.webapi.routes.*

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        // anyHost() // @TODO: Don't do this in production if possible. Try to limit it.

        val cors = PropertiesUtil.getValueString("app.cors")!!
        hosts.addAll(cors.split(',').toMutableSet())
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
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
        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        // /swagger-ui/index.html?url=/openapi.json
        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }
    }

    routing {
        route("api") {
            route("v1") {
                ssr()
            }

        }

    }
    envKind = environment.config.property("ktor.deployment.environment").getString()
    //initDB()
}
private lateinit var envKind: String

/*
    Init Postgresql database connection
 */
fun initDB() {
    val config = HikariConfig("/conf/hikari-$envKind.properties")
    val ds = HikariDataSource(config)
    ds.maxLifetime = 2000
    Database.connect(ds)
}


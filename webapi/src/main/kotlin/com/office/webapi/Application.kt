package com.office.webapi

import com.fasterxml.jackson.databind.SerializationFeature
import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.openAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.schema.namer.DefaultSchemaNamer
import com.papsign.ktor.openapigen.schema.namer.SchemaNamer
import com.prprpr.core.util.PropertiesUtil
import com.prprpr.webapi.routes.ssr
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
//import io.ktor.shared.serializaion.jackson.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

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
    install(OpenAPIGen) {
        // basic info
        info {
            version = "0.0.1"
            title = "Test API"
            description = "The Test API"
            contact {
                name = "Support"
                email = "support@test.com"
            }
        }
        // describe the server, add as many as you want
        server("http://localhost:8080/") {
            description = "Test server"
        }
        //optional custom schema object namer
        replaceModule(DefaultSchemaNamer, object : SchemaNamer {
            val regex = Regex("[A-Za-z0-9_.]+")
            override fun get(type: KType): String {
                return type.toString().replace(regex) { it.value.split(".").last() }.replace(Regex(">|<|, "), "_")
            }
        })
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
        get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
        // /swagger-ui/index.html?url=/openapi.json
        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }
    }

    apiRouting {
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


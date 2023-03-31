package com.office.webapi

import com.office.webapi.plugins.configureHTTP
import com.office.webapi.plugins.configureRouting
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module(testing: Boolean = false) {
    configureHTTP()
    configureRouting()

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


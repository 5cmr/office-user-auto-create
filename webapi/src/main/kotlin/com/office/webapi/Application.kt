package com.office.webapi

import com.office.webapi.plugins.configureHTTP
import com.office.webapi.plugins.configureRouting
import com.prprpr.core.util.PropertiesUtil
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureHTTP()
    configureRouting()


    buildDataSource(environment)
}

/*
    Init Postgresql database connection
 */
fun buildDataSource(environment: ApplicationEnvironment) {
//
//    val envKind = environment.config.property("ktor.deployment.environment").getString()
//    val propertyFileName = if (envKind == "dev") {
//        // 是开发环境
//        "/conf/conf-dev.properties"
//    } else {
//        // prod 生产环境
//        "/conf/conf.properties"
//    }

    val hikariConfig = HikariConfig()
    hikariConfig.poolName = PropertiesUtil.getValueString("dataSource.poolName")
    hikariConfig.jdbcUrl = PropertiesUtil.getValueString("dataSource.jdbcUrl")
    hikariConfig.username = PropertiesUtil.getValueString("dataSource.username")
    hikariConfig.password = PropertiesUtil.getValueString("dataSource.password")
    hikariConfig.driverClassName = PropertiesUtil.getValueString("dataSource.driverClassName")
    val ds = HikariDataSource(hikariConfig)
    ds.maxLifetime = 2000
    Database.connect(ds)
}


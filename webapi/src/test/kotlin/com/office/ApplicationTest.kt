package com.office

import com.office.webapi.controllers.OfficeController
import io.ktor.server.testing.*
import org.junit.Test

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
//        application {
//            configureRouting()
//        }
//        client.get("/").apply {
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
//        }
        val ssrController = OfficeController()
    }
}

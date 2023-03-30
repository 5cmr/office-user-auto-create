package com.office.webapi.routes

import io.ktor.server.routing.*

/**
 * ssr 路由
 */
fun Route.ssr() {
    route("/ssr") {

    }
}
//fun ssr() = routing {
//
//}route("/ssr") {
//    val ssrController = SsrController()
//    tag(TagEnum.SSR) {
//        route("/list").get<Any, ResultBase<List<SsrResultModel>>>(
//            info("获得ssr"),
//            example = ResultBase()
//        ) {
//            val model = ssrController.getList()
//            val result = ResultBase<List<SsrResultModel>>()
//            result.data = model
//
//            respond(result)
//        }
//
//        route("").post<Any, Any, Any>(info("添加")) { params, base ->
//            val url = "https://sspool.herokuapp.com/clash/proxies"
//            val result = HttpRequest.get(url).setHttpProxy("127.0.0.1", 8889).execute().body()
//            val js = result.split("\n")
//            for (index in 0 until  js.count()) {
//                val reg = Regex("^- (.+?)\$")
//                val ms = reg.findAll(js[index]).toList()
//                if (ms.isNotEmpty()) {
//                    val proxyStr = ms[0].groups[1]?.value
//                    val proxyBean = ObjectMapper().readValue(proxyStr, ProxyBean::class.java)
//                    println(proxyBean)
//                }
//            }
//
//            respond(result)
//        }
//    }
//}
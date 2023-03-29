package com.prprpr.webapi.routes

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tag
import com.prprpr.webapi.beans.ProxyBean
import com.prprpr.webapi.controllers.SsrController
import com.prprpr.webapi.resultmodels.ResultBase
import com.prprpr.webapi.resultmodels.SsrResultModel

/**
 * ssr 路由
 */
fun NormalOpenAPIRoute.ssr() = route("/ssr") {
    val ssrController = SsrController()
    tag(TagEnum.SSR) {
        route("/list").get<Any, ResultBase<List<SsrResultModel>>>(
            info("获得ssr"),
            example = ResultBase()
        ) {
            val model = ssrController.getList()
            val result = ResultBase<List<SsrResultModel>>()
            result.data = model

            respond(result)
        }

        route("").post<Any, Any, Any>(info("添加")) { params, base ->
            val url = "https://sspool.herokuapp.com/clash/proxies"
            val result = HttpRequest.get(url).setHttpProxy("127.0.0.1", 8889).execute().body()
            val js = result.split("\n")
            for (index in 0 until  js.count()) {
                val reg = Regex("^- (.+?)\$")
                val ms = reg.findAll(js[index]).toList()
                if (ms.isNotEmpty()) {
                    val proxyStr = ms[0].groups[1]?.value
                    val proxyBean = ObjectMapper().readValue(proxyStr, ProxyBean::class.java)
                    println(proxyBean)
                }
            }

            respond(result)
        }
    }
}
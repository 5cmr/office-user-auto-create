package com.office.webapi.controllers

import cn.hutool.http.HttpRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.prprpr.core.domain.service.SsrService
import com.prprpr.webapi.resultmodels.SsrResultModel
import kotlinx.coroutines.runBlocking

/**
 * ssr 控制器
 */
class SsrController {
    private val tenantId = ""
    private val clientId = ""
    private val clientSecret = ""

    private val ssrService = SsrService()

    fun getList() = runBlocking {
        val dto = ssrService.findAll()

        return@runBlocking dto.map {
            SsrResultModel (
                remarks = it.remarks,
                url = it.url
            )
        }
    }
    fun getAccessToken() = runBlocking {
        val url = "https://login.microsoftonline.com/${tenantId}/oauth2/v2.0/token"
        val body = object {
                val grant_type = "client_credentials"
                val client_id = clientId
                val client_secret = clientSecret
                val scope = "https://graph.microsoft.com/.default"
        }
        val result = HttpRequest.post(url).body(ObjectMapper().writeValueAsBytes(body)).execute().body()

//        val data = await res.json()
//        this.token = data?.access_token || null
    }


}
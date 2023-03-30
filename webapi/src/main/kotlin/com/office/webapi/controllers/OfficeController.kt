package com.office.webapi.controllers

import cn.hutool.http.HttpRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.prprpr.core.domain.service.SsrService
import com.prprpr.webapi.resultmodels.SsrResultModel
import kotlinx.coroutines.runBlocking

/**
 * ssr 控制器
 */
class OfficeController {
    private val tenantId = "62a949a1-6a4a-4cef-aaa1-ce6fdfd884a5"  // 目录(租户) ID
    private val clientId = "b8d2f918-86ad-4f97-80eb-dbe91f20e103"  // 应用程序(客户端) ID
    private val clientSecret = "crc8Q~48r3dE4yyoJpz3OFFL3Mo-f1kQ_GykZds1"

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
    fun createUser(username: string, password: string, domain: string) {
        if (this.token === null)
            throw 'Token is null'

        val url = "https://graph.microsoft.com/v1.0/users"
        val body = object {
            val accountEnabled = true
            val displayName = username
            val mailNickname = username
            val passwordPolicies = "DisablePasswordExpiration, DisableStrongPassword"
            val passwordProfile = object {
                val password = password
                val forceChangePasswordNextSignIn = true
        },
           val userPrincipalName = "${username}@${domain}"
           val usageLocation = "CN"
        }

        val res = HttpRequest.post(url)
            .body(ObjectMapper().writeValueAsBytes(body)).execute().body()


            await fetch(url, {
            method: 'POST',
            headers: {
            'Authorization': `Bearer ${this.token}`,
            'content-type': 'application/json',
        },
            body: JSON.stringify(body)
        })
        val data = await res.json()

        if (res.status !== 201)
            if (data?.error?.message?.includes('userPrincipalName already exists'))
                throw 'Username Exists'
            else
                throw JSON.stringify(data)
    }

    fun assignLicense(email: string, skuId: string) {
        if (this.token === null)
            throw 'Token is null'

        val url = `https://graph.microsoft.com/v1.0/users/${email}/assignLicense`
                const body = {
            addLicenses: [
            {
                disabledPlans: [],
                skuId: skuId
            },
            ],
            removeLicenses: []
        }
        val res = await fetch(url, {
            method: 'POST',
            headers: {
            'Authorization': `Bearer ${this.token}`,
            'Content-Type': 'application/json'
        },
            body: JSON.stringify(body)
        })
        val data = await res.json()

        if (res.status !== 200)
            throw JSON.stringify(data)
    }



}
package com.office.webapi.controllers

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import com.fasterxml.jackson.databind.ObjectMapper


/**
 * ssr 控制器
 */
class OfficeController {
    private val _tenantId = "62a949a1-6a4a-4cef-aaa1-ce6fdfd884a5"  // 目录(租户) ID
    private val _clientId = "b8d2f918-86ad-4f97-80eb-dbe91f20e103"  // 应用程序(客户端) ID
    private val _clientSecret = "crc8Q~48r3dE4yyoJpz3OFFL3Mo-f1kQ_GykZds1"
    private var _token: String? = null

//    private val ssrService = SsrService()

//    fun getList() = runBlocking {
//        val dto = ssrService.findAll()
//
//        return@runBlocking dto.map {
//            SsrResultModel(
//                remarks = it.remarks,
//                url = it.url
//            )
//        }
//    }

    suspend fun getAccessToken() {
        val url = "https://login.microsoftonline.com/${_tenantId}/oauth2/v2.0/token"
        val body = mapOf(
            "grant_type" to "client_credentials",
            "client_id" to _clientId,
            "client_secret" to _clientSecret,
            "scope" to "https://graph.microsoft.com/.default"
        )

        val result = HttpUtil.post(url, body)
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)
        val accessToken = jsonNode["access_token"]
        _token = accessToken.asText() ?: null
    }

    /**
     * 创建用户
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @param domain 域名
     */
    suspend fun createUser(nickname: String, username: String, password: String, domain: String) {
        if (_token == null)
            throw Exception("Token is null")

        val url = "https://graph.microsoft.com/v1.0/users"
        val body = mapOf(
            "accountEnabled" to true,
            "displayName" to nickname,
            "mailNickname" to username,
            "passwordPolicies" to "DisablePasswordExpiration, DisableStrongPassword",
            "passwordProfile" to mapOf(
                "password" to password,
                "forceChangePasswordNextSignIn" to true
            ),
            "userPrincipalName" to "$username@$domain",
            "usageLocation" to "CN"
        )
        val headers = mapOf(
            "Authorization" to "Bearer $_token",
            "content-type" to "application/json"
        )

        val result = HttpRequest
            .post(url).headerMap(headers, false)
            .body(ObjectMapper().writeValueAsString(body)).execute().body()

        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)

        if (jsonNode["status"].asInt() != 201)
            if (jsonNode["error"]["message"]?.asText()?.contains("userPrincipalName already exists") == true)
                throw Exception("Username Exists")
            else
                throw Exception(result)
    }

    suspend fun assignLicense(email: String, skuId: String) {
        if (_token == null)
            throw Exception("Token is null")

        val url = "https://graph.microsoft.com/v1.0/users/$email/assignLicense"
        val body = mapOf(
            "addLicenses" to listOf(
                mapOf(
                    "disabledPlans" to emptyList<String>(),
                    "skuId" to skuId
                )
            ),
            "removeLicenses" to emptyList<String>()
        )

        val headers = mapOf(
            "Authorization" to "Bearer $_token",
            "content-type" to "application/json"
        )

        val result = HttpRequest
            .post(url).headerMap(headers, false)
            .body(ObjectMapper().writeValueAsString(body)).execute().body()

        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)

        if (jsonNode["status"].asInt() != 200)
            throw Exception(result)
    }


}
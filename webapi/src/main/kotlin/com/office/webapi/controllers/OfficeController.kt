package com.office.webapi.controllers

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.office.core.domain.service.ActivationCodeService
import com.prprpr.core.util.PropertiesUtil
import io.ktor.server.locations.*


/**
 * ssr 控制器
 */
class OfficeController {
    @KtorExperimentalLocationsAPI
    @Location("/create")
    data class AccountInput(
        val nickname: String,
        val username: String,
        val password: String,
        val domain: String,
        val skuId: String,
        val code: String
    )

    private var tenantId: String  // 目录(租户) ID
    private var clientId: String  // 应用程序(客户端) ID
    private var clientSecret: String
    private var token: String? = null

    private val activationCodeService = ActivationCodeService()

    init {
        this.tenantId = PropertiesUtil.getValueString("office.tenantId") ?: ""
        this.clientId = PropertiesUtil.getValueString("office.clientId") ?: ""
        this.clientSecret = PropertiesUtil.getValueString("office.clientSecret") ?: ""
    }


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
    fun getConfig(): Map<String, Array<out Any>> {
        val c = mapOf(
            "subscriptions" to arrayOf(
                mapOf(
                    "name" to "E5 开发者订阅",
                    "sku" to "c42b9cae-ea4f-4ab7-9717-81576235ccac"
                )
            ),
            "domains" to arrayOf("yayoo.tk"),
        )
        return c
    }

    @OptIn(KtorExperimentalLocationsAPI::class)
    suspend fun createAccount(request: AccountInput) = runCatching {
        val dto = activationCodeService.find(request.code)
        if (dto != null) {
            // 激活码有效，创建账号
            getAccessToken()
            createUser(request.nickname, request.username, request.password, request.domain)
            assignLicense("${request.username}@${request.domain}", request.skuId)
            // 使激活码无效
            activationCodeService.updateStatus(dto.id, 0)
        } else {
            throw Exception("激活码无效")
        }
    }

    private suspend fun getAccessToken() {
        val url = "https://login.microsoftonline.com/${tenantId}/oauth2/v2.0/token"
        val body = mapOf(
            "grant_type" to "client_credentials",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "scope" to "https://graph.microsoft.com/.default"
        )

        val result = HttpUtil.post(url, body)
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)
        val accessToken = jsonNode["access_token"]
        token = accessToken.asText() ?: null
    }

    /**
     * 创建用户
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @param domain 域名
     */
    private suspend fun createUser(nickname: String, username: String, password: String, domain: String) {
        if (token == null)
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
            "Authorization" to "Bearer $token",
            "content-type" to "application/json"
        )

        val result = HttpRequest
            .post(url).headerMap(headers, false)
            .body(ObjectMapper().writeValueAsString(body)).execute().body()

        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)
        if (jsonNode["error"] != null) {
            if (jsonNode["error"]["message"].asText()
                    .contains("The specified password does not comply with password complexity requirements")
            ) {
                throw Exception("指定的密码不符合密码复杂性要求。")
            } else if (jsonNode["error"]["message"].asText().contains("Password cannot contain username")) {
                throw Exception("密码不能包含用户名。")
            } else if (jsonNode["error"]["message"].asText().contains("userPrincipalName already exists")) {
                throw Exception("用户名已存在。")
            } else
                throw Exception(result)
        }
    }

    private suspend fun assignLicense(email: String, skuId: String) {
        if (token == null)
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
            "Authorization" to "Bearer $token",
            "content-type" to "application/json"
        )

        val result = HttpRequest
            .post(url).headerMap(headers, false)
            .body(ObjectMapper().writeValueAsString(body)).execute().body()

        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(result)

        if (jsonNode["error"] != null) {
            throw Exception(result)
        }
    }


}
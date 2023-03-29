package com.prprpr.webapi.controllers

import com.prprpr.core.domain.service.SsrService
import com.prprpr.webapi.resultmodels.SsrResultModel
import kotlinx.coroutines.runBlocking

/**
 * ssr 控制器
 */
class SsrController {
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


}
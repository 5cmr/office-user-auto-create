package com.office.webapi.controllers

import com.office.core.domain.service.AnnouncementService
import com.office.webapi.resultmodels.AnnouncementResultModel
import kotlinx.coroutines.runBlocking

/**
 * 公告控制器
 */
class AnnouncementController {
    private val announcementService: AnnouncementService = AnnouncementService()

    suspend fun get(id: Long) = runBlocking {
        val dto = announcementService.get(id)

        return@runBlocking AnnouncementResultModel(
            id = dto.id,
            content = dto.content,
            enable = dto.enable,
            createTime = dto.createTime,
            status = dto.status
        )
    }
}
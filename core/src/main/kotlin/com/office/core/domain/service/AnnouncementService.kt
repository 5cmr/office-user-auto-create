package com.office.core.domain.service

import com.office.core.domain.dto.AnnouncementDto
import com.office.core.domain.repository.ActivationCodeRepository
import com.office.core.domain.repository.AnnouncementRepository

class AnnouncementService {
    private val announcementRepository: AnnouncementRepository = AnnouncementRepository()
    /**
     * 查询数据
     * @param id
     * @return 数据
     */
    fun get(id: Long): AnnouncementDto {
        val model = announcementRepository.find(id)
        return AnnouncementDto(
            id = model.id,
            content = model.content,
            enable = model.enable,
            createTime = model.createTime,
            status = model.status
        )

    }
}
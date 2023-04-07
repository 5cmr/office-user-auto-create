package com.office.core.domain.dto

/**
 * 公告数据传输对象
 */
data class AnnouncementDto (
    var id: Long = 0,
    var content: String = "",
    var enable: Boolean = true,
    var createTime: Long = 0,
    var status: Int = 0
)
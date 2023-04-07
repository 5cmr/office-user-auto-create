package com.office.core.domain.entity

/**
 * 公告 表
 */
data class MAnnouncement (
    var id: Long = 0,
    var content: String = "",
    var enable: Boolean = true,
    var createTime: Long = 0,
    var status: Int = 0
)
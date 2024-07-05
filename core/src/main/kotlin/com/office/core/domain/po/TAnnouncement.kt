package com.office.core.domain.po

import org.jetbrains.exposed.sql.Table

/**
 * 公告 表
 */
object TAnnouncement: Table("m_announcement") {
    val id = long("id").autoIncrement()  // id
    val content = text("content")  // 内容
    val enable = bool("enable")  // 是否启用
    val createTime = long("create_time") // 添加时间戳(ms)
    val status = integer("status")  // 状态

    override val primaryKey = PrimaryKey(id) // name is optional here
}
package com.prprpr.core.domain.po

import org.jetbrains.exposed.sql.Table

/**
 * ssr 表
 */
object TSsr: Table("m_ssr") {
    val id = integer("id").autoIncrement()  // id
    val remarks = varchar("remarks", 500)  // 别名
    val url = text("url") // 链接
    val addDate = long("add_date") // 添加时间戳(ms)
    val status = integer("status")  // 状态

    override val primaryKey = PrimaryKey(id) // name is optional here
}
package com.office.core.domain.po

import org.jetbrains.exposed.sql.Table

/**
 * 激活码 表
 */
object TActivationCode: Table("m_activation_code") {
    val id = long("id").autoIncrement()  // id
    val code = varchar("code", 500)  // 激活码
    val addTime = long("add_time") // 添加时间戳(ms)
    val status = integer("status")  // 状态

    override val primaryKey = PrimaryKey(id) // name is optional here
}
package com.office.core.domain.entity

/**
 * 激活码 表
 */
data class MActivationCode(
    var id: Long = 0,
    var code: String = "",
    var addTime: Long = 0,
    var status: Int = 0
)
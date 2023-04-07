package com.office.core.domain.dto

/**
 * 激活码 数据传输对象
 */
data class ActivationCodeDto (
    val id: Long,
    val code: String,
    val createTime: Long,
    val status: Int
)
package com.prprpr.core.domain.dto

/**
 * ssr 数据传输对象
 */
data class SsrDto (
    val id: Int,
    val remarks: String,
    val url: String,
    val addDate: Long,
    val status: Int
)
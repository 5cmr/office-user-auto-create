package com.office.webapi.resultmodels

import io.ktor.http.HttpStatusCode

/**
 * 返回结果
 */
data class ResultBase (
    val success: Boolean = true,  // 是否成功
    val error: Int = HttpStatusCode.OK.value,  // 错误代码
    val errorMsg: String = "",  // 错误信息
    var data: Any? = null  // 数据
)
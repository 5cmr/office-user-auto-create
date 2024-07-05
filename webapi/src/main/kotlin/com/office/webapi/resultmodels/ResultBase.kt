package com.office.webapi.resultmodels

import io.ktor.http.HttpStatusCode

/**
 * 返回结果
 */
data class ResultBase(
    var success: Boolean = true,  // 是否成功
    var code: Int = HttpStatusCode.OK.value,  // 错误代码
    var message: String? = "",  // 错误信息
    var data: Any? = null  // 数据
)
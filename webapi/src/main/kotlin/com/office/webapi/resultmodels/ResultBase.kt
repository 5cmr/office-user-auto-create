package com.office.webapi.resultmodels

import io.ktor.http.HttpStatusCode

/**
 * 返回结果
 */
data class ResultBase<T> (
    val success: Boolean = true,  // 是否成功
    val error: HttpStatusCode = HttpStatusCode.OK,  // 错误代码
    val errorMsg: String = "",  // 错误信息
    var data: T? = null  // 数据
)
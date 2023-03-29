package com.prprpr.webapi.resultmodels

import com.papsign.ktor.openapigen.annotations.Response
import com.papsign.ktor.openapigen.annotations.properties.description.Description
import io.ktor.http.HttpStatusCode

/**
 * 返回结果
 */
@Response("返回结果基类")
data class ResultBase<T> (
    @Description("是否成功")
    val success: Boolean = true,  // 是否成功
    @Description("错误代码")
    val error: HttpStatusCode = HttpStatusCode.OK,  // 错误代码
    @Description("错误信息")
    val errorMsg: String = "",  // 错误信息
    @Description("数据")
    var data: T? = null  // 数据
)
package com.office.webapi.resultmodels

data class AnnouncementResultModel (
    var id: Long = 0,
    var content: String = "",
    var enable: Boolean = true,
    var createTime: Long = 0,
    var status: Int = 0
)
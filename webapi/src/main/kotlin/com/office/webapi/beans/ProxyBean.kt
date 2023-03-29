package com.prprpr.webapi.beans

import com.fasterxml.jackson.annotation.JsonProperty

class ProxyBean {
    val name = ""
    val server = ""
    val port: Int = 0
    val type = ""
    val country = ""
    val password = ""
    val cipher = ""
    val protocol = ""
    @JsonProperty("protocol_param")
    val protocolParam = ""
    val obfs = ""
}
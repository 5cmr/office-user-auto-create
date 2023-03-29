package com.prprpr.webapi.routes

import com.papsign.ktor.openapigen.APITag

enum class TagEnum(override val description: String) : APITag {
    SSR("ssr")
}
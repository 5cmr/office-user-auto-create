package com.prprpr.core

import com.office.core.util.AnalyzeUtil
import org.junit.Test


class ApplicationTest {
    @Test
    fun test() {
       val ana = AnalyzeUtil()
        ana.xiaohongshu("https://www.xiaohongshu.com/explore/6444db3a00000000110122a3?app_platform=ios&app_version=7.84.1&share_from_user_hidden=true&type=normal&xhsshare=CopyLink&appuid=5bbda6201071e10001462fe3&apptime=1682296099")
    }
}

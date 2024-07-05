package com.office.core.util

import cn.hutool.http.HttpUtil
import org.jsoup.Jsoup

class AnalyzeUtil {
    /**
     * 解析短链接
     * @param turl 短链接
     * @return 源地址
     */
    fun duanlianjie(turl: String): String {
        val url = "https://duanwangzhihuanyuan.bmcx.com/web_system/bmcx_com_www/system/file/duanwangzhihuanyuan/get/"
        val body = mapOf(
            "turl" to turl
        )
        val result = HttpUtil.post(url, body)
        val doc = Jsoup.parse(result)
        val text = doc.select("a").text()
        return text
    }

    fun xiaohongshu(pUrl: String): XiaohongshuBean {
        val url = "https://dlpanda.com/zh-CN/xhs?url=$pUrl"
        val result = HttpUtil.get(url)
        val doc = Jsoup.parse(result)
        val info = doc.select(".domain-info-wrap")
        var cardBody = info.select(".card-body.row:nth-child(1)")
        val avatar = cardBody.select("img").attr("src")
        val userId = cardBody.select("div a").attr("href").replace("https://www.xiaohongshu.com/user/profile/", "")
        val nickname = cardBody.select("div a h5").text()

        cardBody = info.select(".card-body.row:nth-child(2)")
        val id = cardBody.select("div:nth-child(1) a").attr("href")
            .replace("https://www.xiaohongshu.com/discovery/item/", "")
        val title = cardBody.select("div:nth-child(1) a h5").text()
        val desc = cardBody.select("div:nth-child(1) p").text()
        val cols = cardBody.select("div.col-md-12.col-lg-6")
        val imageList = arrayListOf<String>()
        for (col in cols) {
            val traceId = col.select("img").attr("src").replace("https://ci.xiaohongshu.com/", "")
            imageList.add(traceId)
        }
        val bean = XiaohongshuBean(
            id = id,
            nickname = nickname,
            userId = userId,
            avatar = avatar,
            title = title,
            desc = desc,
            imageList = imageList
        )
        return bean
    }
}
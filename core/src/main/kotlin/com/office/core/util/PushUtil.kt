package com.office.core.util

import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl


class PushUtil {
//    public fun push() {
//        val service = getService()
//        // 构建模板消息
//        val templateMessage = WxMpTemplateMessage.builder()
//            .templateId(PushConfigure.getTemplateId())
//            .build()
//        templateMessage.addData(WxMpTemplateData("city", weather.getCityName() + "", "#173177"))
//        templateMessage.toUser = userId;
//        service?.sendTemplateMsg(templateMessage)
//    }
//    private fun getService(): WxMpTemplateMsgService? {
//        val wxStorage = WxMpDefaultConfigImpl()
//        wxStorage.appId = "wx2853edd86b5e16d7"
//        wxStorage.secret = "e6e1449b40dbce80ecc83f1045aeff46"
//        val wxMpService: WxMpService = WxMpServiceImpl()
//        wxMpService.wxMpConfigStorage = wxStorage
//        return wxMpService.templateMsgService
//    }
}
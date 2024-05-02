package com.zhaoli.pay.wechat.handler;

import com.zhaoli.pay.service.PayMerchantQrCodeService;
import com.zhaoli.pay.wechat.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {
    @Autowired
    private PayMerchantQrCodeService payMerchantQrCodeService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        boolean scanningRelevanceResult = payMerchantQrCodeService.scanningRelevanceOpenId(wxMpXmlMessage);
        return scanningRelevanceResult ? setResult("关联成功", wxMpXmlMessage, wxMpService) :
                setResult("关联失败", wxMpXmlMessage, wxMpService);
    }

    private WxMpXmlOutMessage setResult(String content, WxMpXmlMessage wxMessage, WxMpService service) {
        return new TextBuilder().build(content, wxMessage, service);
    }
}

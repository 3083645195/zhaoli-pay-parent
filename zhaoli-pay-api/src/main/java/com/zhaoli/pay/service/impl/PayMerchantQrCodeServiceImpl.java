package com.zhaoli.pay.service.impl;

import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.dto.resp.PayMerchantQrCodeRespDTO;
import com.zhaoli.pay.service.PayMerchantQrCodeService;
import com.zhaoli.pay.utils.RedisUtils;
import com.zhaoli.pay.utils.TokenUtils;
import com.zhaoli.pay.wechat.config.WxMpConfiguration;
import com.zhaoli.pay.wechat.config.WxMpProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Service
@Slf4j
public class PayMerchantQrCodeServiceImpl implements PayMerchantQrCodeService {
    @Autowired
    private WxMpProperties wxMpProperties;

    @Override
    public PayMerchantQrCodeRespDTO generateQrCode() {
        String appId = wxMpProperties.getConfigs().get(Constant.DEFAULT_VALUE_ZERO).getAppId();
        WxMpQrcodeService qrcodeService = WxMpConfiguration.getMpServices().get(appId).getQrcodeService();
        // 1.生成二维码sceneId
        String sceneIdStr = TokenUtils.getToken();
        try {
            // 2.生成二维码
            WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(sceneIdStr, Constant.QRCODE_EXPIRE_SECONDS);
            String url = wxMpQrCodeTicket.getUrl();
            return new PayMerchantQrCodeRespDTO(sceneIdStr, url);
        } catch (Exception e) {
            log.error("<e{}>", e);
            return null;
        }
    }

    @Override
    public boolean scanningRelevanceOpenId(WxMpXmlMessage wxMpXmlMessage) {
        //扫码事件处理
        //1.获取二维码中的参数
        String eventKey = wxMpXmlMessage.getEventKey();
        if (StringUtils.isEmpty(eventKey)) {
            return Boolean.FALSE;
        }
        //2.获取用户的 openId
        String openId = wxMpXmlMessage.getFromUser();
        //3.redis中记录该 key用户对应的openId
        RedisUtils.setString(Constant.REDIS_PREFIX_QRCODETOKEN+eventKey, openId);
        return Boolean.TRUE;
    }

    @Override
    public boolean getQrCodeTokenResult(String qrCodenToken) {
        //从 redis 中获取商家的 openId
        String merchantOpenId = RedisUtils.getString(Constant.REDIS_PREFIX_QRCODETOKEN + qrCodenToken);
        return !StringUtils.isEmpty(merchantOpenId);
    }
}

package com.zhaoli.pay.service;

import com.zhaoli.pay.dto.resp.PayMerchantQrCodeRespDTO;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
public interface PayMerchantQrCodeService {
    /**
     * 获取关注微信公众号的链接用于生成二维码
     */
    PayMerchantQrCodeRespDTO generateQrCode();
    /**
     * 扫码关联 openId
     */
    boolean scanningRelevanceOpenId(WxMpXmlMessage wxMpXmlMessage);
    /**
     * 根据二维码查询 redis 判断用户是否扫码成功
     */
    boolean getQrCodeTokenResult(String qrCodenToken);
}

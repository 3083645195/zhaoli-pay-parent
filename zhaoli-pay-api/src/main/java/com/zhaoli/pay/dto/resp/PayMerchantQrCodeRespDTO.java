package com.zhaoli.pay.dto.resp;

import lombok.Data;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */

/**
 * 返回给前端的token 和生成二维码的链接地址
 */
@Data
public class PayMerchantQrCodeRespDTO {
    /**
     * token
     */
    private String qrCodeToken;
    /**
     * 二维码生成链接地址 返回给前端 前端根据该链接生成二维码图片进行展示
     */
    private String url;

    public PayMerchantQrCodeRespDTO() {
    }

    public PayMerchantQrCodeRespDTO(String qrCodeToken, String url) {
        this.qrCodeToken = qrCodeToken;
        this.url = url;
    }
}

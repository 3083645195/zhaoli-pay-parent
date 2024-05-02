package com.zhaoli.pay.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Data
public class NotifyMerchantBO {
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 商户openid
     */
    private String merchantOpenId;
    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;
    /**
     * 支付时间
     */
    private Date payDate;
    /**
     * 支付渠道
     */
    private String paymentChannel;
    /**
     * 支付的id
     */
    private String paymentId;
    public NotifyMerchantBO(){

    }

    public NotifyMerchantBO(String merchantId, String merchantOpenId, BigDecimal paymentAmount, Date payDate, String paymentChannel, String paymentId) {
        this.merchantId = merchantId;
        this.merchantOpenId = merchantOpenId;
        this.paymentAmount = paymentAmount;
        this.payDate = payDate;
        this.paymentChannel = paymentChannel;
        this.paymentId = paymentId;
    }
}

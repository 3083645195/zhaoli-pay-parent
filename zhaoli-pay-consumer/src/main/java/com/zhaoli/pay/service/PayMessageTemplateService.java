package com.zhaoli.pay.service;

import com.zhaoli.pay.bo.NotifyMerchantBO;

/**
 * 异步发送付款消息模板
 * 1.
 */
public interface PayMessageTemplateService {

    /**
     * 通知给商户端支付结果(线程池实现异步方式)
     *
     * merchantId     商户id
     *  merchantOpenId 商户openid
     *  paymentAmount  支付金额
     *  payDate        支付时间
     *  paymentChannel 支付渠道
     *  paymentId      支付id
     * @return
     */
    boolean notifyMerchantPaymentResult(NotifyMerchantBO notifyMerchantBO);

}
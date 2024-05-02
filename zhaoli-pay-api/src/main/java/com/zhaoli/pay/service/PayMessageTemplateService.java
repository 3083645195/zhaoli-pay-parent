package com.zhaoli.pay.service;

import com.zhaoli.pay.bo.NotifyMerchantBO;
import com.zhaoli.pay.entity.PayMerchantInfoDO;

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
    void notifyMerchantPaymentResultThread(NotifyMerchantBO notifyMerchantBO);
    /**
     * 通知给商户端支付结果(MQ方式)
     */
    void notifyMerchantPaymentResultMQ(NotifyMerchantBO notifyMerchantBO);
    /**
     * 通知给商户端支付结果(MQ方式) 生产者批量投递消息
     */
    void notifyMerchantPaymentResultBatchMQ(NotifyMerchantBO notifyMerchantBO);

    /**
     * 商家入驻提交申请公众号通知
     */
    void notifyMerchantSettlementThread(PayMerchantInfoDO payMerchantInfoDO) ;
}
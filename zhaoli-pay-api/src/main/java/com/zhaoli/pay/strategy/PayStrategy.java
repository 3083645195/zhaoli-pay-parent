package com.zhaoli.pay.strategy;

import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;

/**
 * 使用策略模式实现调用不同的支付渠道进行支付
 * 抽象策略类
 */
public interface PayStrategy {
    String toPay(PaymentInfoDO paymentInfoDO,PaymentChannelDO paymentChannelDO);

    /**
     * 同步支付的状态
     * 根据支付id或者订单号码
     * @param paymentInfo
     * @return
     */
    boolean payIdByPayResult(PaymentInfoDO paymentInfo);

}

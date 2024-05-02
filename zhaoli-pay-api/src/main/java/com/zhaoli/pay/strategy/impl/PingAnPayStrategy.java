package com.zhaoli.pay.strategy.impl;


import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.strategy.PayStrategy;
import org.springframework.stereotype.Component;

/**
 * 平安支付
 * 具体策略类
 */
@Component
public class PingAnPayStrategy implements PayStrategy {
    @Override
    public String toPay(PaymentInfoDO paymentInfoDO, PaymentChannelDO paymentChannelDO) {
        return "调用平安支付接口";
    }

    @Override
    public boolean payIdByPayResult(PaymentInfoDO paymentInfo) {
        return false;
    }
}

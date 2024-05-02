package com.zhaoli.pay.holder;

import com.zhaoli.pay.entity.PaymentInfoDO;

import java.util.Map;


public class PaymentInfoHolder {
    private static ThreadLocal<PaymentInfoDO> paymentInfoDo =
            new ThreadLocal<PaymentInfoDO>();

    public static PaymentInfoDO get() {
        return paymentInfoDo.get();
    }

    public static void set(PaymentInfoDO paymentInfo) {
        paymentInfoDo.set(paymentInfo);
    }
}

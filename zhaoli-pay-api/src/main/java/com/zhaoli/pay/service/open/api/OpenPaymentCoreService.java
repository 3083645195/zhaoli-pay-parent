package com.zhaoli.pay.service.open.api;

import com.zhaoli.pay.controller.open.api.dto.req.PaymentInfoDTO;
import com.zhaoli.pay.entity.PaymentInfoDO;

public interface OpenPaymentCoreService {
    /**
     * 将支付信息插入数据库中 并生成token令牌存入redis 中 最后返回令牌
     */
    String createPayToken(PaymentInfoDTO paymentInfoDTO);

    /**
     * 根据 payToken 支付令牌 获取支付交易信息
     */
    PaymentInfoDO getByPayTokenInfo(String payToken);

    /**
     * 根据orderId 查询支付的实际信息
     */
    PaymentInfoDO getOrderIdByPaymentInfo(String orderId);

    /**
     * 修改订单状态为已支付
     */
    int updatePaymentStatus(PaymentInfoDO paymentInfo, Integer paymentStatus);

    /**
     * 修改支付状态为已支付
     */
    int updatePaymentStatusOk(PaymentInfoDO paymentInfo);
}

package com.zhaoli.pay.controller.open.api.dto.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Data
public class PaymentInfoDTO {
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 订单号码 商家给的
     */
    private String orderId;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单内容
     */
    private String orderBody;
}

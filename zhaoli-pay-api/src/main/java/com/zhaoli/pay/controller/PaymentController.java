package com.zhaoli.pay.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.zhaoli.common.core.api.base.BaseApiController;
import com.zhaoli.common.core.api.base.BaseResponse;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.service.open.api.IPaymentChannelService;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import com.zhaoli.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController extends BaseApiController {
    @Autowired
    private OpenPaymentCoreService openPaymentCoreService;
    @Autowired
    private IPaymentChannelService paymentChannelService;

    /**
     * 根据订单号码查询用户支付结果
     *
     * @return
     */
    @GetMapping("/payIdByPayResult/{channelId}")
    public BaseResponse<String> payIdByPayResult(
            @PathVariable String channelId, String orderId) {
        // 1.验证参数
        if (StringUtils.isEmpty(orderId)) {
            return setResultError("orderId is null");
        }
        if (StringUtils.isEmpty(channelId)) {
            return setResultError("channelId is null");
        }
        // 2.查询渠道信息
        PaymentChannelDO channelIdInfo = paymentChannelService.getByChannelIdInfo(channelId);
        if (channelIdInfo == null) {
            return setResultError("渠道信息为null");
        }
        // 3.根据订单id查询 支付信息
        PaymentInfoDO paymentInfo = openPaymentCoreService.
                getOrderIdByPaymentInfo(orderId);
        if (paymentInfo == null) {
            setResultError("未查询支付信息");
        }
        //获取策略类
        String payBeanId = channelIdInfo.getPayBeanId();
        PayStrategy payStrategy = SpringUtil.getBean(payBeanId, PayStrategy.class);
        boolean payIdByPayResult = payStrategy.payIdByPayResult(paymentInfo);
        return payIdByPayResult ? setResultSuccess() :
                setResultError("补偿失败!");
    }


}
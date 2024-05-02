package com.zhaoli.pay.controller.open.api;

import cn.hutool.extra.spring.SpringUtil;
import com.alipay.api.AlipayApiException;
import com.zhaoli.common.core.api.base.BaseApiController;
import com.zhaoli.common.core.api.base.BaseResponse;
import com.zhaoli.pay.controller.open.api.dto.req.PaymentChannelDTO;
import com.zhaoli.pay.controller.open.api.dto.req.PaymentInfoDTO;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.service.open.api.IPaymentChannelService;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import com.zhaoli.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@Slf4j
@RequestMapping("/api/v1")
public class OpenPaymentCoreController extends BaseApiController {
    @Autowired
    private OpenPaymentCoreService openPaymentCoreService;
    @Autowired
    private IPaymentChannelService iPaymentChannelService;

    @PostMapping("/testToPay")
    public BaseResponse<String> testToPay(@RequestBody(required = false) PaymentInfoDTO paymentInfoDTO) {
        return setResultSuccess();
    }

    /**
     * 商家后台形式将支付的内容信息 传递给我们极简支付  极简支付返回对应的支付令牌
     */
    @PostMapping("/createPayToken")
    public BaseResponse<String> createPayToken(@RequestBody PaymentInfoDTO paymentInfoDTO) {
        // 1.验证参数
        String merchantId = paymentInfoDTO.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            return setResultError("merchantId is null");
        }
        BigDecimal paymentAmount = paymentInfoDTO.getPayAmount();
        if (StringUtils.isEmpty(paymentAmount)) {
            return setResultError("paymentAmount is null");
        }
        //2.参数验证完毕之后 生产对应的支付令牌
        String payToken = openPaymentCoreService.createPayToken(paymentInfoDTO);
        if (StringUtils.isEmpty(payToken)) {
            return setResultError();
        }
        log.info("<payToken:{}>", payToken);
        return setResultSuccessData(payToken);
    }

    /**
     * 根据payToken和支付渠道id选择支付方式
     */
    @PostMapping("/toPay")
    public BaseResponse<String> toPay(@RequestBody PaymentChannelDTO paymentChannelDTO) {
        // 利用策略模式+模板方法重构
        String channelId = paymentChannelDTO.getChannelId();
        if (StringUtils.isEmpty(channelId)) {
            return setResultError("channelId is null");
        }
        String payToken = paymentChannelDTO.getPayToken();
        if (StringUtils.isEmpty(payToken)) {
            return setResultError("payToken is null");
        }
        //根据支付令牌查询支付交易信息
        PaymentInfoDO paymentInfoDO = openPaymentCoreService.getByPayTokenInfo(payToken);
        if (paymentInfoDO == null) {
            return setResultError("支付令牌 error");
        }
        //根据渠道id 查询对应的渠道信息
        PaymentChannelDO paymentChannelDO = iPaymentChannelService.getByChannelIdInfo(channelId);
        if (paymentChannelDO == null) {
            return setResultError("渠道有误或者此渠道已经关闭");
        }
        //获取此渠道对应ioc中bean的id
        String payBeanId = paymentChannelDO.getPayBeanId();
        //根据此渠道的bean的id 得到支付策略
        PayStrategy payStrategy = SpringUtil.getBean(payBeanId, PayStrategy.class);
        String toPayHtml = payStrategy.toPay(paymentInfoDO,paymentChannelDO);
        log.info("<toPayHtml:{}>", toPayHtml);
        return setResultSuccessData(toPayHtml);
    }
}

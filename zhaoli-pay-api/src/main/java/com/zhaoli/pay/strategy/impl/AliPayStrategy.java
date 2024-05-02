package com.zhaoli.pay.strategy.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.zhaoli.pay.config.AlipayConfig;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import com.zhaoli.pay.strategy.PayStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 支付宝支付
 * 具体策略类
 */
@Component
@Slf4j
public class AliPayStrategy implements PayStrategy {
    @Autowired
    private OpenPaymentCoreService openPaymentCoreService;
    //获得初始化的AlipayClient
    private AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

    @Override
    @SneakyThrows
    public String toPay(PaymentInfoDO paymentInfoDO, PaymentChannelDO paymentChannelDO) {
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        alipayRequest.setReturnUrl(AlipayConfig.return_url);//同步回调地址
//        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//异步回调地址
        alipayRequest.setReturnUrl(paymentChannelDO.getSyncUrl());//同步回调地址
        alipayRequest.setNotifyUrl(paymentChannelDO.getAsynUrl());//异步回调地址
/**
 *          原来的
 *          //商户订单号，商户网站订单系统中唯一订单号，必填
 *         String out_trade_no = request.getParameter("WIDout_trade_no");
 *         //付款金额，必填
 *         String total_amount = request.getParameter("WIDtotal_amount");
 *         //订单名称，必填
 *         String subject = request.getParameter("WIDsubject");
 *         //商品描述，可空
 *         String body = request.getParameter("WIDbody");
 *         alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
 *                 + "\"total_amount\":\"" + total_amount + "\","
 *                 + "\"subject\":\"" + subject + "\","
 *                 + "\"body\":\"" + body + "\","
 *                 + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
 */
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + paymentInfoDO.getOrderId() + "\"," + "\"total_amount\":\"" + paymentInfoDO.getPayAmount().toString() + "\"," + "\"subject\":\"" + paymentInfoDO.getOrderName() + "\"," + "\"body\":\"" + paymentInfoDO.getOrderBody() + "\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
/**
 *           原来的
 *          //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
 *          alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
 *         		+ "\"total_amount\":\""+ total_amount +"\","
 *         		+ "\"subject\":\""+ subject +"\","
 *         		+ "\"body\":\""+ body +"\","
 *         		+ "\"timeout_express\":\"10m\","
 *         		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
 *         //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
 */

        //请求
        String resultHtml = alipayClient.pageExecute(alipayRequest).getBody();
        return resultHtml;
    }

    @Override
    public boolean payIdByPayResult(PaymentInfoDO paymentInfo) {
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(paymentInfo.getOrderId());
        model.setTradeNo(paymentInfo.getPartyPayId());
        alipayRequest.setBizModel(model);
        try {
            AlipayTradeQueryResponse alipayResponse = alipayClient.execute(alipayRequest);
            log.info("<alipay_response:{}>", alipayResponse);
            // 2.解析json数据 比对金额是否一致
            String respCode = alipayResponse.getCode();
            //判断返回结果是否是10000
            if (!Constant.ALI_PAY_QUERY_RESPONSE_CODE.equals(respCode)) {
                return Boolean.FALSE;
            }
            // 判断支付金额是否与表中一致
            BigDecimal totalAmount = new BigDecimal(alipayResponse.getTotalAmount());
            String outTradeNo = alipayResponse.getOutTradeNo();
            String tradeNo = alipayResponse.getTradeNo();
            String tradeStatus = alipayResponse.getTradeStatus();
            if (!(paymentInfo.getPayAmount().equals(totalAmount) && Constant.ALI_PAY_TRADESTATUS_SUCCESS.equals(tradeStatus))) {
                // 用户实际支付金额与表中存在金额不一致
                log.error("tradeNo:{},outTradeNo:{} 实际金额为：{}", tradeNo, outTradeNo, totalAmount);
                return Boolean.FALSE;
            }
            // 用户实际支付金额与表中存在金额一致的情况下 判断状态 是否已经支付
            Integer paymentStatus = paymentInfo.getPaymentStatus();
            if (Constant.PAY_PAYMENT_COMPLETED.equals(paymentStatus)) {
                return Boolean.TRUE;
            }
            // 则更新表中数据状态
            paymentInfo.setPartyPayId(tradeNo);
            int updateResult = openPaymentCoreService.updatePaymentStatusOk(paymentInfo);
            return updateResult > Constant.DEFAULT_VALUE_ZERO;
        } catch (Exception e) {
            log.error("e:{}", e);
            return Boolean.FALSE;
        }
    }
}


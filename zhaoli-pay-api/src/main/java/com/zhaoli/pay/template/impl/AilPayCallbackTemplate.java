package com.zhaoli.pay.template.impl;

import com.alipay.api.internal.util.AlipaySignature;
import com.zhaoli.pay.config.AlipayConfig;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.enumclass.PaymentEnum;
import com.zhaoli.pay.holder.PayThreadInfoHolder;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import com.zhaoli.pay.template.AbstractPayCallbackTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Controller
@Slf4j
public class AilPayCallbackTemplate extends AbstractPayCallbackTemplate {
    @Autowired
    private OpenPaymentCoreService openPaymentCoreService;

    @Override
    protected String asyncService() {
        // 1.获取上一步流程验签成功参数
        Map<String, String> paramsMap = PayThreadInfoHolder.get();
        // 用户下单生成订单号码
        String outTradeNo = paramsMap.get("out_trade_no");
        // 根据订单号码查询 表中是否存在该笔订单信息
        PaymentInfoDO paymentInfo = getOrderIdByPaymentInfo(outTradeNo);
        // 2.比较用户实际支付金额 下单金额是否一致
        //获取用户下单支付金额
        String totalAmount = paramsMap.get("total_amount");
        BigDecimal payAmount = paymentInfo.getPayAmount();
        if (!comparePaymentAmount(payAmount, new BigDecimal(totalAmount))) {
            // 告诉给我们第三方支付系统不要在继续重试 暂时返回ok 记录订单异常
            return resultOk();
        }
        // 3.同步db的状态
        String tradeNo = paramsMap.get("trade_no");
        // 获取到第三方的支付的id
        paymentInfo.setPartyPayId(tradeNo);
        // 实际支付渠道
        paymentInfo.setPaymentChannel(PaymentEnum.ALIPAY.name());
        return updatePaymentStatusOk(paymentInfo);
    }

    @Override
    protected boolean verifySignature() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            params.remove("channelId");
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                // 验证签名失败的情况下  签名失败 参数 写成日志
                // 补偿 人工干预 先返回 true---告诉第三方支付不要继续重试
                return Boolean.TRUE;
            }
            PayThreadInfoHolder.add(params);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error(">>e:{}<<", e);
            return Boolean.TRUE;
        }
    }


    @Override
    protected String resultOk() {
        return "success";
    }

    @Override
    protected String resultFail() {
        return "fail";
    }
}

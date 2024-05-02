package com.zhaoli.pay.controller;

import com.zhaoli.pay.bo.NotifyMerchantBO;
import com.zhaoli.pay.service.PayMessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@RestController
public class Test {
    @Autowired
    private PayMessageTemplateService payMessageTemplateService;

    @RequestMapping("/test01")
    public String test01() {
        return "test01";
    }

    /**
     * 测试代码
     */
    @GetMapping("/testSend")
    public String testSend(String merchantId,
                           String merchantOpenId,
                           BigDecimal paymentAmount,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date payDate,//它将名为 payDate 的请求参数的值绑定到方法的 payDate 参数上，并使用指定的日期时间格式（“yyyy-MM-dd HH:mm:ss”）对其进行解析
                           String paymentChannel, String paymentId) {
//        boolean result = payMessageTemplateService.notifyMerchantPaymentResults(merchantId, merchantOpenId, paymentAmount, payDate, paymentChannel, paymentId);
        payMessageTemplateService.notifyMerchantPaymentResultMQ(new NotifyMerchantBO(merchantId, merchantOpenId, paymentAmount, payDate, paymentChannel, paymentId));
        return "ok";
    }
}

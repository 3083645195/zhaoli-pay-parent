package com.zhaoli.pay.controller.open.api;

import cn.hutool.extra.spring.SpringUtil;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.service.open.api.IPaymentChannelService;
import com.zhaoli.pay.template.AbstractPayCallbackTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@RestController
@RequestMapping("/callback")
public class PayCallbackCoreController {
    @Autowired
    private IPaymentChannelService iPaymentChannelService;

    /**
     * 同步回调（基于浏览器的形式跳转）
     *
     * @return
     */
    @PostMapping("/synchCallback")
    public String synchCallback() {
        return "ok";
    }

    /**
     * 异步回调的方式 （第三方支付系统采用 异步调用接口的方式）
     *
     * @return
     */
    @RequestMapping("/asynchCallback/{channelId}")
    public String asynchCallback(@PathVariable String channelId) {
        if (StringUtils.isEmpty(channelId)) {
            return "channelId is error";
        }
        //根据渠道id查询到渠道信息
        PaymentChannelDO channelIdInfo = iPaymentChannelService.getByChannelIdInfo(channelId);
        if (channelIdInfo == null) {
            return "渠道错误或关闭";
        }
        //获取渠道的bean的id
        String callbackBeanId = channelIdInfo.getCallbackBeanId();
        //从springioc容器中获取到该bean对象
        AbstractPayCallbackTemplate payCallbackTemplate = SpringUtil.getBean(callbackBeanId, AbstractPayCallbackTemplate.class);
        return payCallbackTemplate.asynCallback();
    }
}

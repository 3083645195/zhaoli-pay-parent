package com.zhaoli.pay.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zhaoli.pay.bo.NotifyMerchantBO;
import com.zhaoli.pay.service.IPayConsumerLogService;
import com.zhaoli.pay.service.PayMessageTemplateService;
import com.zhaoli.pay.wechat.config.WxMpConfiguration;
import com.zhaoli.pay.wechat.config.WxMpProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余胜军
 * @ClassName PayMessageTemplateImpl
 */
@Service
@Slf4j
public class PayMessageTemplateServiceServerImpl implements PayMessageTemplateService {
    @Autowired
    private WxMpProperties wxMpProperties;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private IPayConsumerLogService iPayConsumerLogService;
    /**
     * 模板 id
     */
    @Value("${zhaoli.pay.notifyMerchantTemplateId}")
    private String notifyMerchantTemplateId;

    @Override
    public boolean notifyMerchantPaymentResult(NotifyMerchantBO notifyMerchantBO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(notifyMerchantTemplateId);//模板id
        wxMpTemplateMessage.setToUser(notifyMerchantBO.getMerchantOpenId());
        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", notifyMerchantBO.getMerchantId()));
        data.add(new WxMpTemplateData("keyword1", DateUtil.formatDateTime(notifyMerchantBO.getPayDate())));
        data.add(new WxMpTemplateData("keyword2", notifyMerchantBO.getPaymentChannel()));
        data.add(new WxMpTemplateData("keyword3", notifyMerchantBO.getPaymentAmount().toString()));
        data.add(new WxMpTemplateData("keyword4", notifyMerchantBO.getPaymentId()));
        wxMpTemplateMessage.setData(data);
        wxMpTemplateMessage.setUrl("http://www.mayikt.com");
        try {
            String appId = wxMpProperties.getConfigs().get(0).getAppId();//获取第一个配置对像
            WxMpTemplateMsgService templateMsgService = WxMpConfiguration.getMpServices().get(appId).getTemplateMsgService();
            String messageId = templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
            log.info("messageId:{}", messageId);
            iPayConsumerLogService.addWechatTemplatePayConsumerLog(notifyMerchantBO.getPaymentId(),messageId);
            return Boolean.TRUE;
        } catch (Exception e) {
            // 记录错误日志
            log.error("e:{}", e);
            return Boolean.FALSE;
        }
    }
}

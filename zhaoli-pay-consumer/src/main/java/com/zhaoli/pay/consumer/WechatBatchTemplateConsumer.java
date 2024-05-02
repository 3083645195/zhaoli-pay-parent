package com.zhaoli.pay.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhaoli.pay.bo.NotifyMerchantBO;
import com.zhaoli.pay.config.RabbitMQSpareTireConfig;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.entity.PayConsumerLog;
import com.zhaoli.pay.service.IPayConsumerLogService;
import com.zhaoli.pay.service.PayMessageTemplateService;
import com.zhaoli.pay.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Component
@Slf4j
//监听名为 “fanout_wechatTemplate_queue” 的队列
@RabbitListener(queues = RabbitMQSpareTireConfig.PAY_BATCH_WECHAT_TEMPLATE_QUEUE)
public class WechatBatchTemplateConsumer {
    @Autowired
    private PayMessageTemplateService payMessageTemplateService;
    @Autowired
    private IPayConsumerLogService iPayConsumerLogService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitHandler//在消息消费者中标识具体的方法来处理不同类型的消息
    public void process(String msg, Message message, Channel channel) {
        //用于获取消息的 delivery tag。在 RabbitMQ 中，每条消息都有一个唯一的 delivery tag，用于标识消息在通道中的传递
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //将msg消息开始拆分
            List<NotifyMerchantBO> notifyMerchantBOs = JSONObject.parseArray(msg, NotifyMerchantBO.class);
            log.info("<notifyMerchantBOs:{}>", notifyMerchantBOs);
            notifyMerchantBOs.forEach((notifyMerchantBO) -> {
                String messageId = notifyMerchantBO.getPaymentId();//取出消费id 作为全局的消费id
                //为了解决消息幂等性问题 提前根据消息id查找消费记录 查看子msg是否消费过
                PayConsumerLog byMsgIdPayConsumerLog = iPayConsumerLogService.getByMsgIdPayConsumerLog(messageId);
                if (byMsgIdPayConsumerLog == null) {
                    //该条子msg没有消费过
                    //调用第三方微信公众号模板接口
                    boolean result = payMessageTemplateService.notifyMerchantPaymentResult(notifyMerchantBO);
                    if (!result) {
                        //如果调用第三方微信公众号模板接口发送出现异常发送失败  将该子 msg 存入死信队列中
                        // 不要抛出异常，将该异常消息存入到死信队列中
                        String errorMsg = JSONObject.toJSONString(notifyMerchantBO);
                        amqpTemplate.convertAndSend(RabbitMQSpareTireConfig.RETRY_EXCHANGE, "", errorMsg);
                        log.error("[调用消息模板发送消息失败,将该msg存入死信队列中,notifyMerchantBO:{}]", notifyMerchantBO);
                    }
                }
                //该条消息已经消费过了
                log.info("<notifyMerchantBO:{}，已经消费过，无需重复消费>", notifyMerchantBO);
            });
        } catch (Exception e) {
            log.error("e:{}", e);
            // 1. 记录重试次数 重试多次还是失败的情况下  将批量msg 写日志表 死信队列
            // 让MQ来一次重试
            throw new RuntimeException(e);
        }
    }
}

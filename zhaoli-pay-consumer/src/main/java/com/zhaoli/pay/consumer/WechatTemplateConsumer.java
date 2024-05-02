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

import java.io.IOException;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Component
@Slf4j
//监听名为 “fanout_wechatTemplate_queue” 的队列
@RabbitListener(queues = RabbitMQSpareTireConfig.PAY_WECHAT_TEMPLATE_QUEUE)
public class WechatTemplateConsumer {
    @Autowired
    private PayMessageTemplateService payMessageTemplateService;
    @Autowired
    private IPayConsumerLogService iPayConsumerLogService;
    @Autowired
    private AmqpTemplate amqpTemplate;
//    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    private Integer MAXATTEMPTS=3;

    @RabbitHandler//在消息消费者中标识具体的方法来处理不同类型的消息
    public void process(String msg, Message message, Channel channel) {
        //用于获取消息的 delivery tag。在 RabbitMQ 中，每条消息都有一个唯一的 delivery tag，用于标识消息在通道中的传递
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = null;
        try {
            NotifyMerchantBO notifyMerchantBO = JSONObject.parseObject(msg, NotifyMerchantBO.class);
            log.info("<notifyMerchantBO:{}>", notifyMerchantBO);
            messageId = notifyMerchantBO.getPaymentId();//取出消费id 作为全局的消费id
            //为了解决消息幂等性问题 提前根据消息id查找消费记录 查看是否消费过
            PayConsumerLog byMsgIdPayConsumerLog = iPayConsumerLogService.getByMsgIdPayConsumerLog(messageId);
            if (byMsgIdPayConsumerLog != null) {
                //该条消息已经消费过了
                log.info("<notifyMerchantBO:{}，已经消费过，无需重复消费>", notifyMerchantBO);
//                //手动 ack 成功 会将该 msg 消息从mq中删除
//                channel.basicAck(deliveryTag, Boolean.TRUE);
                return;
            }
            //调用第三方微信公众号模板接口
            boolean result = payMessageTemplateService.notifyMerchantPaymentResult(notifyMerchantBO);
            if (!result) {
                //返回 false 拒绝签收 抛出异常 （mq服务器端间隔一段时间重新消费该 msg）
                log.error("[调用消息模板发送消息失败,notifyMerchantBO:{}]", notifyMerchantBO);
                throw new RuntimeException("调用消息模板接口发送消息失败...");
            }
        } catch (Exception e) {
            log.error("e:{}", e);
//            try {
//                //手动 ack 失败 需要 mq 给我们消费补偿
//                channel.basicNack(deliveryTag, Boolean.TRUE, Boolean.TRUE);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
            //拒绝签收 抛出异常（mq服务器端间隔一段重试该msg）
            //将该msg存放在死信队列中
            // 1.获取该msg被mq重试次数
            String redisSpareTireCount = RedisUtils.getString(Constant.REDIS_PREFIX_MSGID + messageId);
            Integer spareTireCount = StringUtils.isEmpty(redisSpareTireCount) ? Constant.DEFAULT_VALUE_ZERO : Integer.parseInt(redisSpareTireCount);
            //2.计数
            Integer newSpareTireCount = spareTireCount + 1;
            //3.判断是否达到重试阈值 达到 将该msg转移存放死信队列中
            if (newSpareTireCount >= MAXATTEMPTS) {
                amqpTemplate.convertAndSend(RabbitMQSpareTireConfig.RETRY_EXCHANGE, "", msg);
                log.info("消费者消费该msg:{}多次失败，将msg存放到私信队列中", msg);
                //5.需要删除redis对应的msgid记录重试次数
                RedisUtils.delKey(Constant.REDIS_PREFIX_MSGID + messageId);
                return;
            }
            //4.将新的计数存放到redis
            RedisUtils.setString(Constant.REDIS_PREFIX_MSGID + messageId, newSpareTireCount);
            throw new RuntimeException(e);
        }
    }
}

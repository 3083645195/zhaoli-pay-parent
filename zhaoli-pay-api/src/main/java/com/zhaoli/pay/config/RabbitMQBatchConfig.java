package com.zhaoli.pay.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RabbitMQBatchConfig  批量
 */
@Component
public class RabbitMQBatchConfig {
    /**
     * 定义交换机的名称 /pay_batch_ex
     */
    public static final String EXCHANGE_BATCH_MAYIKT_NAME = "/pay_batch_ex";


    /**
     * 批量微信模板队列
     */
    public static final String PAY_BATCH_WECHAT_TEMPLATE_QUEUE = "pay_batch_wechatTemplate_queue";
    /**
     * 缓冲区2条msg 即合并几条msg消息进行批量投递
     */
    public static final Integer BUFFER_SIZE = 5;
    /**
     * 间隔同步时间
     */
    public static final Long BUFFER_INTERVAL_TIME = 1000l;

    /**
     * 配置微信消息模板队列
     *
     * @return
     */
    @Bean
    public Queue fanoutBatchWechatTemplateQueue() {
        return new Queue(PAY_BATCH_WECHAT_TEMPLATE_QUEUE);
    }

    /**
     * 配置 fanoutExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutBatchExchange() {
        return new FanoutExchange(EXCHANGE_BATCH_MAYIKT_NAME);
    }

    // 绑定交换机 Wechattemplate
    @Bean
    public Binding bindingBatchWechattemplateFanoutExchange(Queue fanoutBatchWechatTemplateQueue, FanoutExchange fanoutBatchExchange) {
        return BindingBuilder.bind(fanoutBatchWechatTemplateQueue).to(fanoutBatchExchange);
    }
}
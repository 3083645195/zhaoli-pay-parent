package com.zhaoli.pay.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * rebbitmq
 */
@Component
public class RabbitMQConfig {
    /**
     * 定义交换机的名/pay_ex
     */
    public static final String EXCHANGE_PAY_NAME = "/pay_ex";
    /**
     * 微信模板队列
     */
    public static final String PAY_WECHAT_TEMPLATE_QUEUE = "pay_wechatTemplate_queue";


    /**
     * 配置微信消息模板队列
     *
     * @return
     */
    @Bean
    public Queue fanoutWechatTemplateQueue() {
        return new Queue(PAY_WECHAT_TEMPLATE_QUEUE);
    }

    /**
     * 配置fanoutExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_PAY_NAME);
    }

    /**
     * 关联交换机
     * 根据参数名称在 ioc 容器中获取 Queue 对象
     * 绑定交换机 Wechattemplate
     */
    @Bean
    public Binding bindingWechattemplateFanoutExchange(Queue fanoutWechatTemplateQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutWechatTemplateQueue).to(fanoutExchange);
    }
}

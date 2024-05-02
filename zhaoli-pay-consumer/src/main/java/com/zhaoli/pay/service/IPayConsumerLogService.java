package com.zhaoli.pay.service;

import com.zhaoli.pay.entity.PayConsumerLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-15
 */
public interface IPayConsumerLogService extends IService<PayConsumerLog> {
    /**
     * 根据消息id查找消费记录
     *
     * @return
     */
    PayConsumerLog getByMsgIdPayConsumerLog(String messageId);

    /**
     * 插入调用微信模板消费接口日志
     */
    int addWechatTemplatePayConsumerLog(String messageId, String relationId);
}

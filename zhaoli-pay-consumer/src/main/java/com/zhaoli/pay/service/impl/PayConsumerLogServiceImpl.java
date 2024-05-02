package com.zhaoli.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhaoli.pay.entity.PayConsumerLog;
import com.zhaoli.pay.mapper.PayConsumerLogMapper;
import com.zhaoli.pay.service.IPayConsumerLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-15
 */
@Service
public class PayConsumerLogServiceImpl extends ServiceImpl<PayConsumerLogMapper, PayConsumerLog> implements IPayConsumerLogService {
    @Autowired
    private PayConsumerLogMapper payConsumerLogMapper;
    @Override
    public PayConsumerLog getByMsgIdPayConsumerLog(String messageId) {
        LambdaQueryWrapper<PayConsumerLog> payConsumerLogLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payConsumerLogLambdaQueryWrapper.eq(PayConsumerLog::getMessageId,messageId);
        return payConsumerLogMapper.selectOne(payConsumerLogLambdaQueryWrapper);
    }

    @Override
    public int addWechatTemplatePayConsumerLog(String messageId, String relationId) {
        PayConsumerLog payConsumerLog =
                new PayConsumerLog(messageId, relationId);
        int result = payConsumerLogMapper.insert(payConsumerLog);
        return result;
    }
}
